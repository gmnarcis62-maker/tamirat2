package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import red.line.tamirkar.domain.model.RepairDifficulty
import red.line.tamirkar.domain.model.RepairStep
import red.line.tamirkar.domain.model.TestStep

@Entity(tableName = "repair_guides")
@TypeConverters(RepairGuideConverters::class)
data class RepairGuideEntity(
    @PrimaryKey val id: String,
    val problemId: String,
    val title: String,
    val description: String,
    val steps: List<RepairStep>,
    val warnings: List<String>,
    val tips: List<String>,
    val videoUrl: String?,
    val imageUrls: List<String>,
    val estimatedTime: String,
    val difficulty: RepairDifficulty,
    val requiredTools: List<String>,
    val requiredParts: List<String>,
    val prerequisites: List<String>,
    val testSteps: List<TestStep>
)
