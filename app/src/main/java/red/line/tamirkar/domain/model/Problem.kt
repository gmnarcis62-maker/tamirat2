package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val id: String,
    val title: String,
    val description: String,
    val category: ProblemCategory,
    val severity: ProblemSeverity,
    val symptoms: List<String> = emptyList(),
    val commonCauses: List<String> = emptyList(),
    val estimatedFixTime: String = "",
    val estimatedCost: String = "",
    val difficulty: RepairDifficulty = RepairDifficulty.MEDIUM,
    val requiredTools: List<String> = emptyList(),
    val requiredParts: List<String> = emptyList(),
    val warningNotes: List<String> = emptyList(),
    val successRate: Int = 0,
    val isCommon: Boolean = false,
    val relatedProblems: List<String> = emptyList()
)