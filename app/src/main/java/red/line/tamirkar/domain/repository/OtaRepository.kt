package red.line.tamirkar.domain.repository

import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.domain.model.OtaManifest
import red.line.tamirkar.domain.model.OtaPackage
import red.line.tamirkar.domain.model.DownloadStatus

interface OtaRepository {
    suspend fun fetchManifest(): Result<OtaManifest>
    fun getLocalPackages(): Flow<List<OtaPackage>>
    suspend fun downloadPackage(packageId: String): Flow<DownloadProgress>
    suspend fun installPackage(packageId: String): Result<Int>
    suspend fun cancelDownload(packageId: String)
    suspend fun deletePackage(packageId: String)
    suspend fun checkForUpdates(): Result<List<OtaPackage>>
    suspend fun getInstalledVersion(packageType: red.line.tamirkar.domain.model.PackageType): Int
}

data class DownloadProgress(
    val packageId: String,
    val status: DownloadStatus,
    val progress: Float,
    val bytesDownloaded: Long,
    val totalBytes: Long,
    val errorMessage: String? = null
)
