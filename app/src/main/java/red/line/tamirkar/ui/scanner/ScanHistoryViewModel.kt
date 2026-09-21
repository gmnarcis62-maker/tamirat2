package red.line.tamirkar.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.ScanResult
import javax.inject.Inject

sealed class ScanHistoryUiState {
    object Loading : ScanHistoryUiState()
    data class Success(val scans: List<ScanHistoryItem>) : ScanHistoryUiState()
    object Empty : ScanHistoryUiState()
}

data class ScanHistoryItem(
    val id: String,
    val type: String,
    val content: String,
    val timestamp: Long
)

@HiltViewModel
class ScanHistoryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ScanHistoryUiState>(ScanHistoryUiState.Empty)
    val uiState: StateFlow<ScanHistoryUiState> = _uiState.asStateFlow()

    private val _scans = MutableStateFlow<List<ScanHistoryItem>>(emptyList())

    fun addScan(result: ScanResult) {
        val content = when (result) {
            is ScanResult.BarcodeResult -> result.rawValue
            is ScanResult.TextResult -> result.fullText.take(100)
            else -> ""
        }
        val type = when (result) {
            is ScanResult.BarcodeResult -> "بارکد (${result.type.label})"
            is ScanResult.TextResult -> "متن OCR"
            else -> "نامشخص"
        }
        val newItem = ScanHistoryItem(
            id = java.util.UUID.randomUUID().toString(),
            type = type,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        _scans.value = listOf(newItem) + _scans.value
        refreshState()
    }

    fun deleteScan(id: String) {
        _scans.value = _scans.value.filter { it.id != id }
        refreshState()
    }

    fun clearAll() {
        _scans.value = emptyList()
        refreshState()
    }

    private fun refreshState() {
        _uiState.value = if (_scans.value.isEmpty()) {
            ScanHistoryUiState.Empty
        } else {
            ScanHistoryUiState.Success(_scans.value)
        }
    }
}
