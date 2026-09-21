package red.line.tamirkar.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.AppSettings
import red.line.tamirkar.domain.model.AppTheme
import red.line.tamirkar.domain.repository.SettingsRepository
import javax.inject.Inject

sealed class SettingsUiState {
    object Loading : SettingsUiState()
    data class Success(val settings: AppSettings) : SettingsUiState()
    data class Error(val message: String) : SettingsUiState()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = repository.getSettings()
        .map { SettingsUiState.Success(it) }
        .catch { SettingsUiState.Error(it.localizedMessage ?: "خطا در بارگذاری تنظیمات") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState.Loading
        )

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch { repository.setTheme(theme) }
    }

    fun setDynamicColor(enabled: Boolean) {
        viewModelScope.launch { repository.setDynamicColor(enabled) }
    }

    fun setBiometric(enabled: Boolean) {
        viewModelScope.launch { repository.setBiometric(enabled) }
    }

    fun setAppLock(enabled: Boolean) {
        viewModelScope.launch { repository.setAppLock(enabled) }
    }

    fun setAppLockTimeout(seconds: Int) {
        viewModelScope.launch { repository.setAppLockTimeout(seconds) }
    }

    fun setAutoBackup(enabled: Boolean) {
        viewModelScope.launch { repository.setAutoBackup(enabled) }
    }

    fun setBackupFrequency(days: Int) {
        viewModelScope.launch { repository.setBackupFrequency(days) }
    }

    fun setNotifications(enabled: Boolean) {
        viewModelScope.launch { repository.setNotifications(enabled) }
    }

    fun setSound(enabled: Boolean) {
        viewModelScope.launch { repository.setSound(enabled) }
    }

    fun setHaptic(enabled: Boolean) {
        viewModelScope.launch { repository.setHaptic(enabled) }
    }

    fun setRepairNotifications(enabled: Boolean) {
        viewModelScope.launch { repository.setRepairNotifications(enabled) }
    }

    fun setLowStockNotifications(enabled: Boolean) {
        viewModelScope.launch { repository.setLowStockNotifications(enabled) }
    }

    fun setCompactMode(enabled: Boolean) {
        viewModelScope.launch { repository.setCompactMode(enabled) }
    }

    fun resetToDefaults() {
        viewModelScope.launch { repository.resetToDefaults() }
    }
}
