package red.line.tamirkar.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import red.line.tamirkar.domain.model.DownloadStatus
import red.line.tamirkar.domain.model.OtaManifest
import red.line.tamirkar.domain.model.OtaPackage
import red.line.tamirkar.domain.model.PackageType
import red.line.tamirkar.domain.repository.DownloadProgress
import red.line.tamirkar.domain.repository.OtaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OtaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : OtaRepository {

    override suspend fun fetchManifest(): Result<OtaManifest> {
        return Result.success(
            OtaManifest(
                packages = emptyList(),
                lastUpdated = System.currentTimeMillis(),
                serverVersion = "1.0.0"
            )
        )
    }

    override fun getLocalPackages(): Flow<List<OtaPackage>> = flowOf(emptyList())

    override suspend fun downloadPackage(packageId: String): Flow<DownloadProgress> {
        return flowOf(
            DownloadProgress(
                packageId = packageId,
                status = DownloadStatus.FAILED,
                progress = 0f,
                bytesDownloaded = 0L,
                totalBytes = 0L,
                errorMessage = "دانلود در این نسخه فعال نیست"
            )
        )
    }

    override suspend fun installPackage(packageId: String): Result<Int> {
        return Result.failure(Exception("نصب در این نسخه فعال نیست"))
    }

    override suspend fun cancelDownload(packageId: String) {
        // TODO
    }

    override suspend fun deletePackage(packageId: String) {
        // TODO
    }

    override suspend fun checkForUpdates(): Result<List<OtaPackage>> {
        return Result.success(emptyList())
    }

    override suspend fun getInstalledVersion(packageType: PackageType): Int = 1
}