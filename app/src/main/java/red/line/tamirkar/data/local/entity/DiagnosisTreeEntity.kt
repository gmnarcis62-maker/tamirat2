package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "diagnosis_trees",
    foreignKeys = [
        ForeignKey(entity = ProblemEntity::class, parentColumns = ["id"], childColumns = ["problemId"]),
        ForeignKey(entity = DeviceModelEntity::class, parentColumns = ["id"], childColumns = ["modelId"])
    ],
    indices = [Index("problemId"), Index("modelId")]
)
data class DiagnosisTreeEntity(
    @PrimaryKey val id: String,
    val problemId: String? = null,
    val modelId: String? = null,
    val title: String,
    val description: String? = null,
    val version: Int = 1
)
