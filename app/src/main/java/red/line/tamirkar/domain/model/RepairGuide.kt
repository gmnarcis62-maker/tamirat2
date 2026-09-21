package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RepairGuide(
    val id: String,
    val problemId: String,
    val title: String,
    val description: String,
    val steps: List<RepairStep>,
    val warnings: List<String> = emptyList(),
    val tips: List<String> = emptyList(),
    val videoUrl: String? = null,
    val imageUrls: List<String> = emptyList(),
    val estimatedTime: String = "",
    val difficulty: RepairDifficulty = RepairDifficulty.MEDIUM,
    val requiredTools: List<String> = emptyList(),
    val requiredParts: List<String> = emptyList(),
    val prerequisites: List<String> = emptyList(),
    val testSteps: List<TestStep> = emptyList()
)

@Serializable
data class RepairStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val detailedInstructions: List<String> = emptyList(),
    val warnings: List<String> = emptyList(),
    val imageUrl: String? = null,
    val estimatedTime: String = "",
    val isCritical: Boolean = false,
    val toolsNeeded: List<String> = emptyList(),
    val partsNeeded: List<String> = emptyList()
)

@Serializable
data class TestStep(
    val stepNumber: Int,
    val description: String,
    val expectedResult: String,
    val passAction: String = "",
    val failAction: String = ""
)
