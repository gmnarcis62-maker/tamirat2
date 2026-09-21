package red.line.tamirkar.data.repository

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.BackupRepository
import red.line.tamirkar.domain.repository.RepairRepository
import java.io.*
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: RepairDatabase,
    private val repairRepository: RepairRepository
) : BackupRepository {

    companion object {
        private const val BACKUP_DIR = "backups"
        private const val ALGORITHM = "AES"
        private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
        private const val ITERATIONS = 65536
        private const val KEY_LENGTH = 256
        private const val SALT_LENGTH = 16
        private const val IV_LENGTH = 16
        private const val BACKUP_WORK_NAME = "auto_backup_work"
    }

    private val backupDir: File
        get() = File(context.filesDir, BACKUP_DIR).apply { mkdirs() }

    override suspend fun createBackup(password: String?): BackupResult {
        return withContext(Dispatchers.IO) {
            try {
                val timestamp = System.currentTimeMillis()
                val fileName = "tamirkar_backup_${timestamp}.zip"
                val backupFile = File(backupDir, fileName)

                // Collect data
                val customers = repairRepository.getAllCustomers()
                val repairs = repairRepository.getAllRepairJobs()
                val inventory = repairRepository.getAllInventoryItems()
                val invoices = repairRepository.getAllInvoices()

                val recordCount = BackupRecordCount(
                    customers = customers.size,
                    repairs = repairs.size,
                    inventory = inventory.size,
                    invoices = invoices.size,
                    total = customers.size + repairs.size + inventory.size + invoices.size
                )

                val metadata = BackupMetadata(
                    version = 1,
                    appVersion = "1.0.0",
                    createdAt = timestamp,
                    deviceId = getDeviceId(),
                    isEncrypted = password != null,
                    recordCount = recordCount
                )

                // Create JSON data
                val backupData = BackupData(
                    metadata = metadata,
                    customers = customers,
                    repairs = repairs,
                    inventory = inventory,
                    invoices = invoices
                )

                val jsonData = Json.encodeToString(backupData)

                // Write to zip
                ZipOutputStream(FileOutputStream(backupFile)).use { zos ->
                    // Metadata entry
                    zos.putNextEntry(ZipEntry("metadata.json"))
                    zos.write(Json.encodeToString(metadata).toByteArray(Charsets.UTF_8))
                    zos.closeEntry()

                    // Data entry (encrypted if password provided)
                    zos.putNextEntry(ZipEntry("data"))
                    val dataBytes = if (password != null) {
                        encryptData(jsonData.toByteArray(Charsets.UTF_8), password)
                    } else {
                        jsonData.toByteArray(Charsets.UTF_8)
                    }
                    zos.write(dataBytes)
                    zos.closeEntry()
                }

                BackupResult.Success(
                    filePath = backupFile.absolutePath,
                    fileSize = backupFile.length(),
                    timestamp = timestamp,
                    recordCount = recordCount
                )
            } catch (e: Exception) {
                BackupResult.Error("خطا در ایجاد پشتیبان: ${e.localizedMessage}", e)
            }
        }
    }

    override suspend fun restoreBackup(filePath: String, password: String?): BackupResult {
        return withContext(Dispatchers.IO) {
            try {
                val backupFile = File(filePath)
                if (!backupFile.exists()) {
                    return@withContext BackupResult.Error("فایل پشتیبان یافت نشد")
                }

                var backupData: BackupData? = null

                ZipInputStream(FileInputStream(backupFile)).use { zis ->
                    var entry: ZipEntry?
                    while (zis.nextEntry.also { entry = it } != null) {
                        when (entry!!.name) {
                            "metadata.json" -> {
                                // Read metadata (not used directly but validates structure)
                                val metadataBytes = zis.readBytes()
                                Json.decodeFromString<BackupMetadata>(
                                    metadataBytes.toString(Charsets.UTF_8)
                                )
                            }
                            "data" -> {
                                val dataBytes = zis.readBytes()
                                val decryptedBytes = if (password != null) {
                                    decryptData(dataBytes, password)
                                } else {
                                    dataBytes
                                }
                                backupData = Json.decodeFromString(
                                    decryptedBytes.toString(Charsets.UTF_8)
                                )
                            }
                        }
                        zis.closeEntry()
                    }
                }

                backupData?.let { data ->
                    // Restore customers
                    data.customers.forEach { repairRepository.saveCustomer(it) }
                    // Restore repairs
                    data.repairs.forEach { repairRepository.saveRepairJob(it) }
                    // Restore inventory
                    data.inventory.forEach { repairRepository.saveInventoryItem(it) }
                    // Restore invoices
                    data.invoices.forEach { repairRepository.saveInvoice(it) }

                    BackupResult.Success(
                        filePath = filePath,
                        fileSize = backupFile.length(),
                        timestamp = System.currentTimeMillis(),
                        recordCount = data.metadata.recordCount
                    )
                } ?: BackupResult.Error("فایل پشتیبان نامعتبر است")
            } catch (e: Exception) {
                BackupResult.Error("خطا در بازیابی پشتیبان: ${e.localizedMessage}", e)
            }
        }
    }

    override suspend fun verifyBackup(filePath: String, password: String?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val backupFile = File(filePath)
                if (!backupFile.exists()) return@withContext false

                var hasMetadata = false
                var hasData = false

                ZipInputStream(FileInputStream(backupFile)).use { zis ->
                    var entry: ZipEntry?
                    while (zis.nextEntry.also { entry = it } != null) {
                        when (entry!!.name) {
                            "metadata.json" -> {
                                val bytes = zis.readBytes()
                                Json.decodeFromString<BackupMetadata>(
                                    bytes.toString(Charsets.UTF_8)
                                )
                                hasMetadata = true
                            }
                            "data" -> {
                                val bytes = zis.readBytes()
                                if (password != null) {
                                    decryptData(bytes, password)
                                }
                                hasData = true
                            }
                        }
                        zis.closeEntry()
                    }
                }
                hasMetadata && hasData
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun getBackupMetadata(filePath: String): BackupMetadata? {
        return withContext(Dispatchers.IO) {
            try {
                val backupFile = File(filePath)
                if (!backupFile.exists()) return@withContext null

                var metadata: BackupMetadata? = null
                ZipInputStream(FileInputStream(backupFile)).use { zis ->
                    var entry: ZipEntry?
                    while (zis.nextEntry.also { entry = it } != null) {
                        if (entry!!.name == "metadata.json") {
                            val bytes = zis.readBytes()
                            metadata = Json.decodeFromString(
                                bytes.toString(Charsets.UTF_8)
                            )
                        }
                        zis.closeEntry()
                    }
                }
                metadata
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun listBackups(): Flow<List<BackupInfo>> = flow {
        val backups = backupDir.listFiles()?.filter {
            it.isFile && it.name.endsWith(".zip")
        }?.mapNotNull { file ->
            val metadata = getBackupMetadata(file.absolutePath)
            metadata?.let {
                BackupInfo(
                    filePath = file.absolutePath,
                    fileName = file.name,
                    fileSize = file.length(),
                    createdAt = it.createdAt,
                    isEncrypted = it.isEncrypted,
                    recordCount = it.recordCount
                )
            }
        }?.sortedByDescending { it.createdAt } ?: emptyList()
        emit(backups)
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteBackup(filePath: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                File(filePath).delete()
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun scheduleAutoBackup(enabled: Boolean, intervalDays: Int) {
        val workManager = WorkManager.getInstance(context)
        if (enabled) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()

            val request = PeriodicWorkRequestBuilder<AutoBackupWorker>(
                intervalDays.toLong(), java.util.concurrent.TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                BACKUP_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
        } else {
            workManager.cancelUniqueWork(BACKUP_WORK_NAME)
        }
    }

    override suspend fun exportToExternal(backupFilePath: String, destinationPath: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val source = File(backupFilePath)
                val dest = File(destinationPath)
                source.inputStream().use { input ->
                    dest.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun encryptData(data: ByteArray, password: String): ByteArray {
        val salt = ByteArray(SALT_LENGTH).apply { SecureRandom().nextBytes(this) }
        val iv = ByteArray(IV_LENGTH).apply { SecureRandom().nextBytes(this) }

        val key = deriveKey(password, salt)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        val encrypted = cipher.doFinal(data)

        // Format: [SALT (16)] [IV (16)] [ENCRYPTED DATA]
        return salt + iv + encrypted
    }

    private fun decryptData(data: ByteArray, password: String): ByteArray {
        val salt = data.copyOfRange(0, SALT_LENGTH)
        val iv = data.copyOfRange(SALT_LENGTH, SALT_LENGTH + IV_LENGTH)
        val encrypted = data.copyOfRange(SALT_LENGTH + IV_LENGTH, data.size)

        val key = deriveKey(password, salt)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return cipher.doFinal(encrypted)
    }

    private fun deriveKey(password: String, salt: ByteArray): SecretKeySpec {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(
            password.toCharArray(), salt, ITERATIONS, KEY_LENGTH
        )
        val secretKey = factory.generateSecret(spec)
        return SecretKeySpec(secretKey.encoded, ALGORITHM)
    }

    private fun getDeviceId(): String {
        return "${context.packageName}_${System.currentTimeMillis()}"
    }

    @kotlinx.serialization.Serializable
    private data class BackupData(
        val metadata: BackupMetadata,
        val customers: List<Customer>,
        val repairs: List<RepairJob>,
        val inventory: List<InventoryItem>,
        val invoices: List<Invoice>
    )
}
