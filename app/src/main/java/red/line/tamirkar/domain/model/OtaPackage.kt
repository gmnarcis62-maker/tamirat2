package red.line.tamirkar.domain.model

import red.line.tamirkar.core.util.PersianDateUtil

data class OtaPackage(
    val id: String,
    val name: String,
    val description: String,
    val version: Int,
    val versionName: String,
    val packageType: PackageType,
    val downloadUrl: String,
    val fileSize: Long,
    val checksum: String,
    val recordCount: Int,
    val minAppVersion: String? = null,
    val publishedAt: Long,
    val isInstalled: Boolean = false,
    val installedAt: Long? = null,
    val downloadProgress: Float = 0f,
    val downloadStatus: DownloadStatus = DownloadStatus.IDLE
) {
    val publishedAtFormatted: String
        get() = PersianDateUtil.formatTimestamp(publishedAt)

    val fileSizeFormatted: String
        get() = when {
            fileSize < 1024 -> "$fileSize B"
            fileSize < 1024 * 1024 -> "${fileSize / 1024} KB"
            else -> "${fileSize / (1024 * 1024)} MB"
        }
}

enum class PackageType(val label: String) {
    BRANDS("برندها"),
    MODELS("مدل‌ها"),
    PROBLEMS("مشکلات"),
    DIAGNOSIS("درخت عیب‌یابی"),
    FULL("کامل"),
    PATCH("وصله")
}

enum class DownloadStatus(val label: String) {
    IDLE("آماده دانلود"),
    PENDING("در صف"),
    DOWNLOADING("در حال دانلود"),
    DOWNLOADED("دانلود شده"),
    INSTALLING("در حال نصب"),
    INSTALLED("نصب شده"),
    FAILED("خطا"),
    CANCELLED("لغو شده")
}

data class OtaManifest(
    val packages: List<OtaPackage>,
    val lastUpdated: Long,
    val serverVersion: String
)
