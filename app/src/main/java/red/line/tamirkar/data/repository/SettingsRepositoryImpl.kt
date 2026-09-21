package red.line.tamirkar.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import red.line.tamirkar.domain.model.AppSettings
import red.line.tamirkar.domain.model.AppTheme
import red.line.tamirkar.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AppSettings>
) : SettingsRepository {

    override fun getSettings(): Flow<AppSettings> = dataStore.data

    override suspend fun setTheme(theme: AppTheme) {
        dataStore.updateData { it.copy(theme = theme) }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.updateData { it.copy(dynamicColorEnabled = enabled) }
    }

    override suspend fun setBiometric(enabled: Boolean) {
        dataStore.updateData { it.copy(biometricEnabled = enabled) }
    }

    override suspend fun setAppLock(enabled: Boolean) {
        dataStore.updateData { it.copy(appLockEnabled = enabled) }
    }

    override suspend fun setAppLockTimeout(seconds: Int) {
        dataStore.updateData { it.copy(appLockTimeoutSeconds = seconds) }
    }

    override suspend fun setAutoBackup(enabled: Boolean) {
        dataStore.updateData { it.copy(autoBackupEnabled = enabled) }
    }

    override suspend fun setBackupFrequency(days: Int) {
        dataStore.updateData { it.copy(backupFrequencyDays = days) }
    }

    override suspend fun setNotifications(enabled: Boolean) {
        dataStore.updateData { it.copy(notificationsEnabled = enabled) }
    }

    override suspend fun setSound(enabled: Boolean) {
        dataStore.updateData { it.copy(soundEnabled = enabled) }
    }

    override suspend fun setHaptic(enabled: Boolean) {
        dataStore.updateData { it.copy(hapticEnabled = enabled) }
    }

    override suspend fun setRepairNotifications(enabled: Boolean) {
        dataStore.updateData { it.copy(showRepairNotifications = enabled) }
    }

    override suspend fun setLowStockNotifications(enabled: Boolean) {
        dataStore.updateData { it.copy(showLowStockNotifications = enabled) }
    }

    override suspend fun setCompactMode(enabled: Boolean) {
        dataStore.updateData { it.copy(compactMode = enabled) }
    }

    override suspend fun resetToDefaults() {
        dataStore.updateData { AppSettings() }
    }
}
