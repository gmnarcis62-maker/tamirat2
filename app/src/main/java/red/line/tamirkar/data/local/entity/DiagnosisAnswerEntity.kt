package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "diagnosis_answers",
    foreignKeys = [
        ForeignKey(entity = DiagnosisSessionEntity::class, parentColumns = ["id"], childColumns = ["sessionId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = DiagnosisNodeEntity::class, parentColumns = ["id"], childColumns = ["nodeId"]),
        ForeignKey(entity = DiagnosisOptionEntity::class, parentColumns = ["id"], childColumns = ["optionId"])
    ],
    indices = [Index("sessionId"), Index("nodeId"), Index("optionId")]
)
data class DiagnosisAnswerEntity(
    @PrimaryKey val id: String,
    val sessionId: String,
    val nodeId: String,
    val optionId: String,
    val value: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
