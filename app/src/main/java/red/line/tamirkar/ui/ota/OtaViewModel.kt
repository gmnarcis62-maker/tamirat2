package red.line.tamirkar.ui.ota

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.DownloadProgress
import red.line.tamirkar.domain.repository.OtaRepository
import javax.inject.Inject

sealed class OtaUiState {
    object Loading : OtaUiState()
    data class Success(
        val packages: List<OtaPackage>,
        val availableUpdates: Int = 0,
        val isRefreshing: Boolean = false
    ) : OtaUiState()
    data class Error(val message: String) : OtaUiState()
}

@HiltViewModel
class OtaViewModel @Inject constructor(
    private val otaRepository: OtaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<OtaUiState>(OtaUiState.Loading)
    val uiState: StateFlow<OtaUiState> = _uiState.asStateFlow()

    private val _downloadProgress = MutableStateFlow<Map<String, DownloadProgress>>(emptyMap())
    val downloadProgress: StateFlow<Map<String, DownloadProgress>> = _downloadProgress.asStateFlow()

    private val _installationResult = MutableSharedFlow<Pair<String, Result<Int>>>()
    val installationResult: SharedFlow<Pair<String, Result<Int>>> = _installationResult.asSharedFlow()

    init {
        loadPackages()
    }

    fun loadPackages() {
        viewModelScope.launch {
            _uiState.value = OtaUiState.Loading
            try {
                otaRepository.getLocalPackages().collect { packages ->
                    val updates = packages.count { !it.isInstalled && it.downloadStatus == DownloadStatus.IDLE }
                    _uiState.value = OtaUiState.Success(
                        packages = packages,
                        availableUpdates = updates
                    )
                }
            } catch (e: Exception) {
                _uiState.value = OtaUiState.Error("خطا در بارگذاری پکیج‌ها: ${e.localizedMessage}")
            }
        }
    }

    fun refresh() {
        val current = _uiState.value as? OtaUiState.Success ?: return
        _uiState.value = current.copy(isRefreshing = true)
        loadPackages()
    }

    fun downloadPackage(packageId: String) {
        viewModelScope.launch {
            try {
                otaRepository.downloadPackage(packageId).collect { progress ->
                    _downloadProgress.value = _downloadProgress.value + (packageId to progress)
                }
            } catch (e: Exception) {
                _downloadProgress.value = _downloadProgress.value + (packageId to DownloadProgress(
                    packageId = packageId,
                    status = DownloadStatus.FAILED,
                    progress = 0f,
                    bytesDownloaded = 0,
                    totalBytes = 0,
                    errorMessage = e.localizedMessage
                ))
            }
        }
    }

    fun installPackage(packageId: String) {
        viewModelScope.launch {
            val result = otaRepository.installPackage(packageId)
            _installationResult.emit(packageId to result)
            if (result.isSuccess) {
                loadPackages()
            }
        }
    }

    fun cancelDownload(packageId: String) {
        viewModelScope.launch {
            otaRepository.cancelDownload(packageId)
            _downloadProgress.value = _downloadProgress.value - packageId
        }
    }

    fun deletePackage(packageId: String) {
        viewModelScope.launch {
            otaRepository.deletePackage(packageId)
            loadPackages()
        }
    }

    fun checkForUpdates() {
        viewModelScope.launch {
            try {
                val result = otaRepository.checkForUpdates()
                result.getOrNull()?.let { updates ->
                    val current = _uiState.value as? OtaUiState.Success ?: return@let
                    _uiState.value = current.copy(availableUpdates = updates.size)
                }
            } catch (e: Exception) {
                // Silently handle
            }
        }
    }
}
