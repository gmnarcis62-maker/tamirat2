package red.line.tamirkar.domain.model

import androidx.camera.core.ExperimentalGetImage

sealed class ScanResult {
    data class BarcodeResult(
        val rawValue: String,
        val format: BarcodeFormat,
        val type: BarcodeType,
        val displayValue: String? = null
    ) : ScanResult()

    data class TextResult(
        val fullText: String,
        val blocks: List<TextBlock>
    ) : ScanResult()

    object NoResult : ScanResult()
}

enum class BarcodeFormat {
    QR_CODE, AZTEC, DATA_MATRIX,
    UPC_A, UPC_E, EAN_8, EAN_13,
    CODE_39, CODE_93, CODE_128,
    ITF, PDF417, UNKNOWN;

    val label: String
        get() = when (this) {
            QR_CODE -> "QR Code"
            AZTEC -> "Aztec"
            DATA_MATRIX -> "Data Matrix"
            UPC_A -> "UPC-A"
            UPC_E -> "UPC-E"
            EAN_8 -> "EAN-8"
            EAN_13 -> "EAN-13"
            CODE_39 -> "Code 39"
            CODE_93 -> "Code 93"
            CODE_128 -> "Code 128"
            ITF -> "ITF"
            PDF417 -> "PDF417"
            UNKNOWN -> "نامشخص"
        }
}

enum class BarcodeType(val label: String) {
    IMEI("IMEI"),
    SERIAL("سریال"),
    CONTACT_INFO("اطلاعات تماس"),
    EMAIL("ایمیل"),
    ISBN("ISBN"),
    PRODUCT("محصول"),
    TEXT("متن"),
    URL("لینک"),
    WIFI("WiFi"),
    UNKNOWN("نامشخص")
}

data class TextBlock(
    val text: String,
    val confidence: Float? = null
)
