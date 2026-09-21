package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "diagnosis_options",
    foreignKeys = [ForeignKey(entity = DiagnosisNodeEntity::class, parentColumns = ["id"], childColumns = ["nodeId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("nodeId"), Index("nextNodeId")]
)
data class DiagnosisOptionEntity(
    @PrimaryKey val id: String,
    val nodeId: String,
    val title: String,
    val value: String? = null,
    val nextNodeId: String? = null,
    val condition: String? = null
)
