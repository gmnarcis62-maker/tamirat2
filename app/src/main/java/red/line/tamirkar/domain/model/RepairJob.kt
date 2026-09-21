package red.line.tamirkar.domain.model

data class RepairJob(
    val id: String,
    val customerId: String,
    val customerName: String,
    val customerPhone: String,
    val deviceModelId: String? = null,
    val deviceModelName: String? = null,
    val customerDeviceId: String? = null,
    val reportedProblem: String,
    val technicianDiagnosis: String? = null,
    val status: RepairStatus,
    val totalCost: Double? = null,
    val partsCost: Double? = null,
    val laborCost: Double? = null,
    val receivedAt: Long,
    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val deliveredAt: Long? = null,
    val beforePhotos: List<String> = emptyList(),
    val afterPhotos: List<String> = emptyList(),
    val voiceNotePath: String? = null,
    val notes: String? = null
)

enum class RepairStatus(val label: String, val colorHex: String) {
    RECEIVED("دریافت شده", "#FF9800"),
    DIAGNOSING("در حال عیب‌یابی", "#2196F3"),
    WAITING_PARTS("در انتظار قطعه", "#9C27B0"),
    IN_PROGRESS("در حال تعمیر", "#F44336"),
    COMPLETED("تکمیل شده", "#4CAF50"),
    DELIVERED("تحویل داده شده", "#009688"),
    CANCELLED("لغو شده", "#757575")
}
