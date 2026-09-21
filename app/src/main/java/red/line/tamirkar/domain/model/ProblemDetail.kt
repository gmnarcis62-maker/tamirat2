package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProblemDetail(
    val id: String,
    val title: String,
    val category: ProblemCategory,
    val severity: ProblemSeverity,
    val description: String,
    val symptoms: List<String> = emptyList(),
    val causes: List<ProblemCause> = emptyList(),
    val solutions: List<ProblemSolution> = emptyList(),
    val repairGuide: RepairGuide? = null,
    val estimatedTime: String = "",
    val estimatedCost: CostEstimate? = null,
    val requiredTools: List<String> = emptyList(),
    val requiredParts: List<String> = emptyList(),
    val difficulty: RepairDifficulty = RepairDifficulty.MEDIUM,
    val warningNotes: List<String> = emptyList(),
    val videoUrl: String? = null,
    val imageUrls: List<String> = emptyList(),
    val relatedProblems: List<String> = emptyList(),
    val isCommon: Boolean = false,
    val successRate: Int = 0,
    val brandSpecific: Map<String, List<String>> = emptyMap(),
    val modelSpecific: Map<String, List<String>> = emptyMap()
)

enum class ProblemCategory(val label: String, val icon: String) {
    POWER("تغذیه و روشن نشدن", "power"),
    DISPLAY("نمایشگر و تاچ", "display"),
    CHARGING("شارژ و باتری", "charging"),
    NETWORK("شبکه و آنتن", "network"),
    AUDIO("صدا و میکروفن", "audio"),
    SOFTWARE("نرم‌افزار و سیستم", "software"),
    CAMERA("دوربین", "camera"),
    CONNECTIVITY("اتصالات", "connectivity"),
    HARDWARE("سخت‌افزار", "hardware"),
    WATER("آب‌خوردگی", "water")
}

enum class ProblemSeverity(val label: String, val color: String) {
    LOW("کم", "#4CAF50"),
    MEDIUM("متوسط", "#FF9800"),
    HIGH("زیاد", "#F44336"),
    CRITICAL("بحرانی", "#9C27B0")
}

enum class RepairDifficulty(val label: String, val stars: Int) {
    EASY("آسان", 1),
    MEDIUM("متوسط", 2),
    HARD("سخت", 3),
    EXPERT("تخصصی", 4)
}

@Serializable
data class ProblemCause(
    val id: String,
    val description: String,
    val probability: Int = 0,
    val testMethod: String = "",
    val confirmationSigns: List<String> = emptyList()
)

@Serializable
data class ProblemSolution(
    val id: String,
    val description: String,
    val steps: List<String> = emptyList(),
    val estimatedCost: CostEstimate? = null,
    val successRate: Int = 0,
    val timeRequired: String = "",
    val requiresProfessional: Boolean = false,
    val isTemporary: Boolean = false
)

@Serializable
data class CostEstimate(
    val min: Double,
    val max: Double,
    val currency: String = "تومان",
    val notes: String = ""
)

@Serializable
data class Tool(
    val id: String,
    val name: String,
    val description: String = "",
    val isRequired: Boolean = true,
    val alternative: String = ""
)

@Serializable
data class Part(
    val id: String,
    val name: String,
    val partNumber: String = "",
    val description: String = "",
    val estimatedPrice: Double? = null,
    val isOptional: Boolean = false,
    val compatibility: List<String> = emptyList()
)