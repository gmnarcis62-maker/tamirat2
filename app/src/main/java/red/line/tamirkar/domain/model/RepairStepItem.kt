package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RepairStepItem(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val warning: String? = null,
    val estimatedTime: Int = 0
)