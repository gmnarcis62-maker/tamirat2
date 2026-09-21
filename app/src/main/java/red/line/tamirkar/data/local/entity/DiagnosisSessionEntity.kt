package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "diagnosis_sessions",
    foreignKeys = [
        ForeignKey(entity = DeviceModelEntity::class, parentColumns = ["id"], childColumns = ["modelId"]),
        ForeignKey(entity = ProblemEntity::class, parentColumns = ["id"], childColumns = ["problemId"])
    ],
    indices = [Index("modelId"), Index("problemId"), Index("status")]
)
data class DiagnosisSessionEntity(
    @PrimaryKey val id: String,
    val repairCaseId: String? = null,
    val modelId: String? = null,
    val problemId: String? = null,
    val startedAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null,
    val currentNodeId: String? = null,
    val status: String = "IN_PROGRESS"
)
