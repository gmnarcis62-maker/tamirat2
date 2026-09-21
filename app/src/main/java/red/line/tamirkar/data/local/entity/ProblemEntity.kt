package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.model.RepairDifficulty

@Entity(tableName = "problems")
@TypeConverters(ProblemConverters::class)
data class ProblemEntity(
    @PrimaryKey val id: String,
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
    val warningNotes: List<String>,
    val successRate: Int,
    val isCommon: Boolean,
    val relatedProblems: List<String>
)
