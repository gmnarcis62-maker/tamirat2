package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DiagnosisNode(
    val id: String,
    val question: String,
    val description: String? = null,
    val options: List<DiagnosisOption>,
    val isStartNode: Boolean = false,
    val isEndNode: Boolean = false,
    val problemId: String? = null,
    val guideId: String? = null,
    val severity: ProblemSeverity? = null
)

@Serializable
data class DiagnosisOption(
    val label: String,
    val nextNodeId: String? = null,
    val problemId: String? = null,
    val guideId: String? = null,
    val severity: ProblemSeverity? = null,
    val advice: String? = null
)
