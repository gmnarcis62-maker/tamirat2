package red.line.tamirkar.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.BarcodeFormat
import red.line.tamirkar.domain.model.BarcodeType
import red.line.tamirkar.domain.model.ScanResult
import javax.inject.Inject

sealed class ScannerUiState {
    object Idle : ScannerUiState()
    object Scanning : ScannerUiState()
    data class BarcodeDetected(val result: ScanResult.BarcodeResult) : ScannerUiState()
    data class TextDetected(val result: ScanResult.TextResult) : ScannerUiState()
    data class Error(val message: String) : ScannerUiState()
}

@HiltViewModel
class ScannerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ScannerUiState>(ScannerUiState.Idle)
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    private val _scannerMode = MutableStateFlow(ScannerMode.BARCODE)
    val scannerMode: StateFlow<ScannerMode> = _scannerMode.asStateFlow()

    private val _flashEnabled = MutableStateFlow(false)
    val flashEnabled: StateFlow<Boolean> = _flashEnabled.asStateFlow()

    private val _isCopied = MutableStateFlow(false)
    val isCopied: StateFlow<Boolean> = _isCopied.asStateFlow()

    fun setMode(mode: ScannerMode) {
        _scannerMode.value = mode
        _uiState.value = ScannerUiState.Idle
    }

    fun toggleFlash() {
        _flashEnabled.value = !_flashEnabled.value
    }

    fun onBarcodeDetected(rawValue: String, format: BarcodeFormat) {
        val type = detectBarcodeType(rawValue)
        val result = ScanResult.BarcodeResult(
            rawValue = rawValue,
            format = format,
            type = type,
            displayValue = formatDisplayValue(rawValue, type)
        )
        _uiState.value = ScannerUiState.BarcodeDetected(result)
    }

    fun onTextDetected(fullText: String, blocks: List<String>) {
        val textBlocks = blocks.map { red.line.tamirkar.domain.model.TextBlock(it) }
        val result = ScanResult.TextResult(fullText = fullText, blocks = textBlocks)
        _uiState.value = ScannerUiState.TextDetected(result)
    }

    fun onError(message: String) {
        _uiState.value = ScannerUiState.Error(message)
    }

    fun reset() {
        _uiState.value = ScannerUiState.Idle
    }

    fun markAsCopied() {
        viewModelScope.launch {
            _isCopied.value = true
            kotlinx.coroutines.delay(2000)
            _isCopied.value = false
        }
    }

    private fun detectBarcodeType(rawValue: String): BarcodeType {
        return when {
            rawValue.matches(Regex("^\*#06#.*")) -> BarcodeType.IMEI
            rawValue.matches(Regex("^[0-9]{14,16}$")) -> BarcodeType.IMEI
            rawValue.matches(Regex("^[A-Za-z0-9]{10,30}$")) -> BarcodeType.SERIAL
            rawValue.startsWith("http://") || rawValue.startsWith("https://") -> BarcodeType.URL
            rawValue.startsWith("WIFI:") -> BarcodeType.WIFI
            rawValue.startsWith("MATMSG:") || rawValue.startsWith("mailto:") -> BarcodeType.EMAIL
            rawValue.startsWith("BEGIN:VCARD") -> BarcodeType.CONTACT_INFO
            rawValue.matches(Regex("^(97[89])\d{10}$")) -> BarcodeType.ISBN
            else -> BarcodeType.TEXT
        }
    }

    private fun formatDisplayValue(rawValue: String, type: BarcodeType): String {
        return when (type) {
            BarcodeType.IMEI -> "IMEI: $rawValue"
            BarcodeType.SERIAL -> "سریال: $rawValue"
            BarcodeType.URL -> rawValue
            BarcodeType.WIFI -> rawValue.removePrefix("WIFI:")
            BarcodeType.EMAIL -> rawValue
            BarcodeType.CONTACT_INFO -> "اطلاعات تماس"
            else -> rawValue
        }
    }
}
