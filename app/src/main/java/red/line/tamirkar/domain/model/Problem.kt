package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val id: String,
    val title: String,
    val description: String,
    val category: ProblemCategory,
    val severity: ProblemSeverity,
    val symptoms: List<String>,
    val commonCauses: List<String>,
    val estimatedFixTime: String,
    val estimatedCost: String,
    val difficulty: RepairDifficulty,
    val requiredTools: List<String>,
    val requiredParts: List<String>,
    val warningNotes: List<String> = emptyList(),
    val successRate: Int = 0,
    val isCommon: Boolean = false,
    val relatedProblems: List<String> = emptyList()
)

enum class ProblemCategory(val label: String) {
    POWER("تغذیه"),
    DISPLAY("نمایشگر"),
    CHARGING("شارژ"),
    NETWORK("شبکه"),
    AUDIO("صدا"),
    SOFTWARE("نرم‌افزار"),
    HARDWARE("سخت‌افزار"),
    CONNECTIVITY("اتصالات"),
    CAMERA("دوربین"),
    SENSOR("سنسور"),
    BATTERY("باتری"),
    BOARD("برد")
}

enum class ProblemSeverity(val label: String, val color: Long) {
    LOW("کم", 0xFF4CAF50),
    MEDIUM("متوسط", 0xFFFF9800),
    HIGH("زیاد", 0xFFF44336),
    CRITICAL("بحرانی", 0xFFB71C1C)
}

enum class RepairDifficulty(val label: String, val stars: Int) {
    EASY("آسان", 1),
    MEDIUM("متوسط", 2),
    HARD("سخت", 3),
    EXPERT("تخصصی", 4)
}
