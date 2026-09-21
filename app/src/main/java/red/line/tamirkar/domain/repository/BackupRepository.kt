package red.line.tamirkar.domain.repository

import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.domain.model.BackupInfo
import red.line.tamirkar.domain.model.BackupMetadata
import red.line.tamirkar.domain.model.BackupResult

interface BackupRepository {
    suspend fun createBackup(password: String? = null): BackupResult
    suspend fun restoreBackup(filePath: String, password: String? = null): BackupResult
    suspend fun verifyBackup(filePath: String, password: String? = null): Boolean
    suspend fun getBackupMetadata(filePath: String): BackupMetadata?
    fun listBackups(): Flow<List<BackupInfo>>
    suspend fun deleteBackup(filePath: String): Boolean
    suspend fun scheduleAutoBackup(enabled: Boolean, intervalDays: Int)
    suspend fun exportToExternal(backupFilePath: String, destinationPath: String): Boolean
}
