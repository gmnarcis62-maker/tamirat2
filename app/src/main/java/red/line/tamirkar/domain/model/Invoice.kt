package red.line.tamirkar.domain.model

import red.line.tamirkar.core.util.PersianDateUtil

data class Invoice(
    val id: String,
    val repairCaseId: String? = null,
    val customerName: String,
    val customerPhone: String,
    val deviceModel: String? = null,
    val invoiceNumber: String,
    val items: List<InvoiceItem>,
    val laborCost: Double = 0.0,
    val discount: Double = 0.0,
    val totalAmount: Double,
    val finalAmount: Double,
    val isPaid: Boolean = false,
    val paymentMethod: PaymentMethod? = null,
    val pdfPath: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) {
    val createdAtFormatted: String
        get() = PersianDateUtil.formatTimestamp(createdAt)
}

data class InvoiceItem(
    val id: String,
    val name: String,
    val quantity: Int = 1,
    val unitPrice: Double,
    val totalPrice: Double = quantity * unitPrice
)

enum class PaymentMethod(val label: String) {
    CASH("نقدی"),
    CARD("کارت به کارت"),
    CHECK("چک"),
    INSTALLMENT("اقساط")
}
