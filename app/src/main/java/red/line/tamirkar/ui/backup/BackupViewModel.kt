package red.line.tamirkar.ui.backup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.BackupInfo
import red.line.tamirkar.domain.model.BackupMetadata
import red.line.tamirkar.domain.model.BackupResult
import red.line.tamirkar.domain.repository.BackupRepository
import javax.inject.Inject

sealed class BackupUiState {
    object Idle : BackupUiState()
    object Loading : BackupUiState()
    data class CreateSuccess(val result: BackupResult.Success) : BackupUiState()
    data class RestoreSuccess(val result: BackupResult.Success) : BackupUiState()
    data class Error(val message: String) : BackupUiState()
}

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val backupRepository: BackupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BackupUiState>(BackupUiState.Idle)
    val uiState: StateFlow<BackupUiState> = _uiState.asStateFlow()

    val backups: StateFlow<List<BackupInfo>> = backupRepository.listBackups()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedBackup = MutableStateFlow<BackupInfo?>(null)
    val selectedBackup: StateFlow<BackupInfo?> = _selectedBackup.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _showPasswordField = MutableStateFlow(false)
    val showPasswordField: StateFlow<Boolean> = _showPasswordField.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun togglePasswordField() {
        _showPasswordField.value = !_showPasswordField.value
    }

    fun selectBackup(backup: BackupInfo?) {
        _selectedBackup.value = backup
    }

    fun createBackup() {
        viewModelScope.launch {
            _isProcessing.value = true
            _uiState.value = BackupUiState.Loading
            try {
                val pass = _password.value.ifBlank { null }
                val result = backupRepository.createBackup(password = pass)
                _uiState.value = when (result) {
                    is BackupResult.Success -> BackupUiState.CreateSuccess(result)
                    is BackupResult.Error -> BackupUiState.Error(result.message)
                }
            } catch (e: Exception) {
                _uiState.value = BackupUiState.Error("خطا در ایجاد پشتیبان: ${e.localizedMessage}")
            } finally {
                _isProcessing.value = false
            }
        }
    }

    fun restoreBackup(filePath: String) {
        viewModelScope.launch {
            _isProcessing.value = true
            _uiState.value = BackupUiState.Loading
            try {
                val pass = _password.value.ifBlank { null }
                val result = backupRepository.restoreBackup(filePath, password = pass)
                _uiState.value = when (result) {
                    is BackupResult.Success -> BackupUiState.RestoreSuccess(result)
                    is BackupResult.Error -> BackupUiState.Error(result.message)
                }
            } catch (e: Exception) {
                _uiState.value = BackupUiState.Error("خطا در بازیابی: ${e.localizedMessage}")
            } finally {
                _isProcessing.value = false
            }
        }
    }

    fun deleteBackup(filePath: String) {
        viewModelScope.launch {
            backupRepository.deleteBackup(filePath)
        }
    }

    fun verifyBackup(filePath: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val pass = _password.value.ifBlank { null }
            val isValid = backupRepository.verifyBackup(filePath, password = pass)
            onResult(isValid)
        }
    }

    fun resetState() {
        _uiState.value = BackupUiState.Idle
        _password.value = ""
        _showPasswordField.value = false
    }
}
