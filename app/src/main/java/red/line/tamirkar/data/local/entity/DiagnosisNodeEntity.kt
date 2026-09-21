package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import red.line.tamirkar.domain.model.DiagnosisOption
import red.line.tamirkar.domain.model.ProblemSeverity

@Entity(tableName = "diagnosis_nodes")
@TypeConverters(DiagnosisConverters::class)
data class DiagnosisNodeEntity(
    @PrimaryKey val id: String,
    val question: String,
    val description: String?,
    val options: List<DiagnosisOption>,
    val isStartNode: Boolean,
    val isEndNode: Boolean,
    val problemId: String?,
    val guideId: String?,
    val severity: ProblemSeverity?
)
