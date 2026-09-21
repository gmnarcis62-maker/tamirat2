package red.line.tamirkar.domain.model

import red.line.tamirkar.core.util.PersianDateUtil

sealed class BackupResult {
    data class Success(
        val filePath: String,
        val fileSize: Long,
        val timestamp: Long,
        val recordCount: BackupRecordCount
    ) : BackupResult()

    data class Error(val message: String, val exception: Throwable? = null) : BackupResult()
}

data class BackupRecordCount(
    val customers: Int = 0,
    val repairs: Int = 0,
    val inventory: Int = 0,
    val invoices: Int = 0,
    val total: Int = 0
)

data class BackupMetadata(
    val version: Int = 1,
    val appVersion: String = "1.0.0",
    val createdAt: Long = System.currentTimeMillis(),
    val deviceId: String = "",
    val isEncrypted: Boolean = true,
    val recordCount: BackupRecordCount = BackupRecordCount()
) {
    val createdAtFormatted: String
        get() = PersianDateUtil.formatTimestamp(createdAt)
}

data class BackupInfo(
    val filePath: String,
    val fileName: String,
    val fileSize: Long,
    val createdAt: Long,
    val isEncrypted: Boolean,
    val recordCount: BackupRecordCount
) {
    val createdAtFormatted: String
        get() = PersianDateUtil.formatTimestamp(createdAt)

    val fileSizeFormatted: String
        get() = when {
            fileSize < 1024 -> "$fileSize B"
            fileSize < 1024 * 1024 -> "${fileSize / 1024} KB"
            else -> "${fileSize / (1024 * 1024)} MB"
        }
}
