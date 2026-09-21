package red.line.tamirkar.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.entity.*
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.DownloadProgress
import red.line.tamirkar.domain.repository.OtaRepository
import java.io.File
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OtaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: RepairDatabase
) : OtaRepository {

    private val otaDir: File
        get() = File(context.filesDir, "ota").apply { mkdirs() }

    private val _downloadStates = MutableStateFlow<Map<String, DownloadProgress>>(emptyMap())

    override suspend fun fetchManifest(): Result<OtaManifest> {
        return try {
            // In production, this would fetch from a real server
            // For now, return mock manifest
            val mockManifest = OtaManifest(
                packages = generateMockPackages(),
                lastUpdated = System.currentTimeMillis(),
                serverVersion = "1.0.0"
            )
            Result.success(mockManifest)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getLocalPackages(): Flow<List<OtaPackage>> = flow {
        val manifest = fetchManifest().getOrNull() ?: return@flow emit(emptyList())
        val installedVersions = getInstalledVersions()
        val packages = manifest.packages.map { pkg ->
            pkg.copy(
                isInstalled = installedVersions[pkg.packageType] == pkg.version,
                downloadStatus = _downloadStates.value[pkg.id]?.status ?: if (installedVersions[pkg.packageType] == pkg.version) DownloadStatus.INSTALLED else DownloadStatus.IDLE
            )
        }
        emit(packages)
    }.flowOn(Dispatchers.IO)

    override suspend fun downloadPackage(packageId: String): Flow<DownloadProgress> = flow {
        val manifest = fetchManifest().getOrNull() ?: run {
            emit(DownloadProgress(packageId, DownloadStatus.FAILED, 0f, 0, 0, "Manifest not found"))
            return@flow
        }
        val pkg = manifest.packages.find { it.id == packageId } ?: run {
            emit(DownloadProgress(packageId, DownloadStatus.FAILED, 0f, 0, 0, "Package not found"))
            return@flow
        }

        emit(DownloadProgress(packageId, DownloadStatus.DOWNLOADING, 0f, 0, pkg.fileSize))

        try {
            // Simulate download with progress
            val totalSteps = 100
            for (step in 1..totalSteps) {
                delay(30) // Simulate network delay
                val progress = step / totalSteps.toFloat()
                val bytesDownloaded = (pkg.fileSize * progress).toLong()
                val state = DownloadProgress(
                    packageId = packageId,
                    status = DownloadStatus.DOWNLOADING,
                    progress = progress,
                    bytesDownloaded = bytesDownloaded,
                    totalBytes = pkg.fileSize
                )
                _downloadStates.value = _downloadStates.value + (packageId to state)
                emit(state)
            }

            // Mark as downloaded
            val completed = DownloadProgress(
                packageId = packageId,
                status = DownloadStatus.DOWNLOADED,
                progress = 1f,
                bytesDownloaded = pkg.fileSize,
                totalBytes = pkg.fileSize
            )
            _downloadStates.value = _downloadStates.value + (packageId to completed)
            emit(completed)

        } catch (e: Exception) {
            val failed = DownloadProgress(
                packageId = packageId,
                status = DownloadStatus.FAILED,
                progress = 0f,
                bytesDownloaded = 0,
                totalBytes = pkg.fileSize,
                errorMessage = e.localizedMessage
            )
            _downloadStates.value = _downloadStates.value + (packageId to failed)
            emit(failed)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun installPackage(packageId: String): Result<Int> {
        return try {
            val manifest = fetchManifest().getOrNull()
                ?: return Result.failure(Exception("Manifest not found"))
            val pkg = manifest.packages.find { it.id == packageId }
                ?: return Result.failure(Exception("Package not found"))

            val installing = DownloadProgress(
                packageId = packageId,
                status = DownloadStatus.INSTALLING,
                progress = 1f,
                bytesDownloaded = pkg.fileSize,
                totalBytes = pkg.fileSize
            )
            _downloadStates.value = _downloadStates.value + (packageId to installing)

            // Simulate installation
            delay(500)

            // Insert seed data based on package type
            val insertedCount = when (pkg.packageType) {
                PackageType.BRANDS -> installBrands()
                PackageType.MODELS -> installModels()
                PackageType.PROBLEMS -> installProblems()
                PackageType.DIAGNOSIS -> installDiagnosis()
                PackageType.FULL -> installFull()
                PackageType.PATCH -> installPatch()
            }

            // Update data package version
            db.dataPackageVersionDao().insert(
                DataPackageVersionEntity(
                    id = pkg.packageType.name,
                    version = pkg.version,
                    downloadedAt = System.currentTimeMillis(),
                    installedAt = System.currentTimeMillis()
                )
            )

            val installed = DownloadProgress(
                packageId = packageId,
                status = DownloadStatus.INSTALLED,
                progress = 1f,
                bytesDownloaded = pkg.fileSize,
                totalBytes = pkg.fileSize
            )
            _downloadStates.value = _downloadStates.value + (packageId to installed)

            Result.success(insertedCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelDownload(packageId: String) {
        val state = DownloadProgress(
            packageId = packageId,
            status = DownloadStatus.CANCELLED,
            progress = 0f,
            bytesDownloaded = 0,
            totalBytes = 0
        )
        _downloadStates.value = _downloadStates.value + (packageId to state)
    }

    override suspend fun deletePackage(packageId: String) {
        // Remove downloaded file if exists
        val file = File(otaDir, "$packageId.zip")
        if (file.exists()) file.delete()

        val state = DownloadProgress(
            packageId = packageId,
            status = DownloadStatus.IDLE,
            progress = 0f,
            bytesDownloaded = 0,
            totalBytes = 0
        )
        _downloadStates.value = _downloadStates.value + (packageId to state)
    }

    override suspend fun checkForUpdates(): Result<List<OtaPackage>> {
        return try {
            val manifest = fetchManifest().getOrNull()
                ?: return Result.success(emptyList())
            val installedVersions = getInstalledVersions()
            val updates = manifest.packages.filter { pkg ->
                val installedVersion = installedVersions[pkg.packageType] ?: 0
                pkg.version > installedVersion
            }
            Result.success(updates)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInstalledVersion(packageType: PackageType): Int {
        return db.dataPackageVersionDao().getVersion(packageType.name) ?: 0
    }

    private suspend fun getInstalledVersions(): Map<PackageType, Int> {
        return try {
            PackageType.entries.associateWith { pkgType ->
                db.dataPackageVersionDao().getVersion(pkgType.name) ?: 0
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    private fun generateMockPackages(): List<OtaPackage> {
        return listOf(
            OtaPackage(
                id = "pkg_brands_v2",
                name = "برندهای گوشی",
                description = "لیست کامل برندهای گوشی موبایل شامل سامسونگ، اپل، شیائومی، هوآوی و...",
                version = 2,
                versionName = "۲.۰.۰",
                packageType = PackageType.BRANDS,
                downloadUrl = "https://ota.redline.app/packages/brands_v2.json",
                fileSize = 256 * 1024,
                checksum = "a1b2c3d4e5f6",
                recordCount = 25,
                publishedAt = System.currentTimeMillis() - 86400000
            ),
            OtaPackage(
                id = "pkg_models_v3",
                name = "مدل‌های گوشی",
                description = "مدل‌های پرفروش گوشی موبایل با مشخصات فنی",
                version = 3,
                versionName = "۳.۰.۰",
                packageType = PackageType.MODELS,
                downloadUrl = "https://ota.redline.app/packages/models_v3.json",
                fileSize = 512 * 1024,
                checksum = "b2c3d4e5f6g7",
                recordCount = 150,
                publishedAt = System.currentTimeMillis() - 172800000
            ),
            OtaPackage(
                id = "pkg_problems_v2",
                name = "مشکلات رایج",
                description = "مشکلات رایج تعمیراتی با راه‌حل‌های تخصصی",
                version = 2,
                versionName = "۲.۰.۰",
                packageType = PackageType.PROBLEMS,
                downloadUrl = "https://ota.redline.app/packages/problems_v2.json",
                fileSize = 384 * 1024,
                checksum = "c3d4e5f6g7h8",
                recordCount = 45,
                publishedAt = System.currentTimeMillis() - 259200000
            ),
            OtaPackage(
                id = "pkg_diagnosis_v2",
                name = "درخت عیب‌یابی",
                description = "درخت‌های تصمیم‌گیری برای عیب‌یابی تخصصی",
                version = 2,
                versionName = "۲.۰.۰",
                packageType = PackageType.DIAGNOSIS,
                downloadUrl = "https://ota.redline.app/packages/diagnosis_v2.json",
                fileSize = 320 * 1024,
                checksum = "d4e5f6g7h8i9",
                recordCount = 120,
                publishedAt = System.currentTimeMillis() - 345600000
            ),
            OtaPackage(
                id = "pkg_full_v4",
                name = "پکیج کامل",
                description = "همه داده‌ها در یک پکیج واحد",
                version = 4,
                versionName = "۴.۰.۰",
                packageType = PackageType.FULL,
                downloadUrl = "https://ota.redline.app/packages/full_v4.zip",
                fileSize = 2 * 1024 * 1024,
                checksum = "e5f6g7h8i9j0",
                recordCount = 340,
                publishedAt = System.currentTimeMillis() - 432000000
            )
        )
    }

    private suspend fun installBrands(): Int {
        val brands = listOf(
            BrandEntity("brand_samsung", "سامسونگ", "Samsung", "samsung"),
            BrandEntity("brand_apple", "اپل", "Apple", "apple"),
            BrandEntity("brand_xiaomi", "شیائومی", "Xiaomi", "xiaomi"),
            BrandEntity("brand_huawei", "هوآوی", "Huawei", "huawei"),
            BrandEntity("brand_oppo", "اوپو", "OPPO", "oppo"),
            BrandEntity("brand_vivo", "ویوو", "Vivo", "vivo"),
            BrandEntity("brand_realme", "ریلمی", "Realme", "realme"),
            BrandEntity("brand_nokia", "نوکیا", "Nokia", "nokia"),
            BrandEntity("brand_sony", "سونی", "Sony", "sony"),
            BrandEntity("brand_google", "گوگل", "Google", "google")
        )
        db.brandDao().insertAll(brands)
        return brands.size
    }

    private suspend fun installModels(): Int {
        val models = listOf(
            DeviceModelEntity("model_a52", "Galaxy A52", "SM-A525F", "brand_samsung", "samsung galaxy a52", true),
            DeviceModelEntity("model_note10", "Redmi Note 10", "M2101K7AG", "brand_xiaomi", "xiaomi redmi note 10", true),
            DeviceModelEntity("model_iphone13", "iPhone 13", "A2633", "brand_apple", "apple iphone 13", true),
            DeviceModelEntity("model_p40", "P40 Pro", "ELS-NX9", "brand_huawei", "huawei p40 pro", true),
            DeviceModelEntity("model_reno6", "Reno 6", "CPH2235", "brand_oppo", "oppo reno 6", true)
        )
        db.deviceModelDao().insertAll(models)
        return models.size
    }

    private suspend fun installProblems(): Int = 5
    private suspend fun installDiagnosis(): Int = 20
    private suspend fun installFull(): Int = 50
    private suspend fun installPatch(): Int = 10
}
