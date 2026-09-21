package red.line.tamirkar.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import red.line.tamirkar.domain.model.BackupInfo
import red.line.tamirkar.domain.model.BackupMetadata
import red.line.tamirkar.domain.model.BackupRecordCount
import red.line.tamirkar.domain.model.BackupResult
import red.line.tamirkar.domain.repository.BackupRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BackupRepository {

    private val backupDir: File
        get() = File(context.filesDir, "backups").apply { if (!exists()) mkdirs() }

    override suspend fun createBackup(password: String?): BackupResult {
        return try {
            val timestamp = System.currentTimeMillis()
            val fileName = "backup_$timestamp.json"
            val file = File(backupDir, fileName)
            file.writeText("{\"version\":1,\"createdAt\":$timestamp}")

            BackupResult.Success(
                filePath = file.absolutePath,
                fileSize = file.length(),
                timestamp = timestamp,
                recordCount = BackupRecordCount()
            )
        } catch (e: Exception) {
            BackupResult.Error("خطا در ساخت پشتیبان: ${e.localizedMessage}", e)
        }
    }

    override suspend fun restoreBackup(filePath: String, password: String?): BackupResult {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                return BackupResult.Error("فایل پشتیبان یافت نشد")
            }
            BackupResult.Success(
                filePath = file.absolutePath,
                fileSize = file.length(),
                timestamp = System.currentTimeMillis(),
                recordCount = BackupRecordCount()
            )
        } catch (e: Exception) {
            BackupResult.Error("خطا در بازیابی: ${e.localizedMessage}", e)
        }
    }

    override suspend fun verifyBackup(filePath: String, password: String?): Boolean {
        return try {
            File(filePath).exists()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getBackupMetadata(filePath: String): BackupMetadata? {
        return try {
            val file = File(filePath)
            if (!file.exists()) return null
            BackupMetadata(
                createdAt = file.lastModified(),
                isEncrypted = false,
                recordCount = BackupRecordCount()
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun listBackups(): Flow<List<BackupInfo>> {
        return flowOf(
            try {
                backupDir.listFiles()
                    ?.filter { it.isFile && it.name.endsWith(".json") }
                    ?.map { file ->
                        BackupInfo(
                            filePath = file.absolutePath,
                            fileName = file.name,
                            fileSize = file.length(),
                            createdAt = file.lastModified(),
                            isEncrypted = false,
                            recordCount = BackupRecordCount()
                        )
                    }
                    ?.sortedByDescending { it.createdAt }
                    ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        )
    }

    override suspend fun deleteBackup(filePath: String): Boolean {
        return try {
            File(filePath).delete()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun scheduleAutoBackup(enabled: Boolean, intervalDays: Int) {
        // TODO: پیاده‌سازی WorkManager
    }

    override suspend fun exportToExternal(backupFilePath: String, destinationPath: String): Boolean {
        return try {
            val source = File(backupFilePath)
            val dest = File(destinationPath)
            if (!source.exists()) return false
            source.copyTo(dest, overwrite = true)
            true
        } catch (e: Exception) {
            false
        }
    }
}