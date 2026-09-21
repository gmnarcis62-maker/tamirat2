package red.line.tamirkar.domain.repository

import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.domain.model.AppSettings
import red.line.tamirkar.domain.model.AppTheme

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun setTheme(theme: AppTheme)
    suspend fun setDynamicColor(enabled: Boolean)
    suspend fun setBiometric(enabled: Boolean)
    suspend fun setAppLock(enabled: Boolean)
    suspend fun setAppLockTimeout(seconds: Int)
    suspend fun setAutoBackup(enabled: Boolean)
    suspend fun setBackupFrequency(days: Int)
    suspend fun setNotifications(enabled: Boolean)
    suspend fun setSound(enabled: Boolean)
    suspend fun setHaptic(enabled: Boolean)
    suspend fun setRepairNotifications(enabled: Boolean)
    suspend fun setLowStockNotifications(enabled: Boolean)
    suspend fun setCompactMode(enabled: Boolean)
    suspend fun resetToDefaults()
}
