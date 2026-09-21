package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "problem_symptoms",
    primaryKeys = ["problemId", "symptomId"],
    foreignKeys = [
        ForeignKey(entity = ProblemEntity::class, parentColumns = ["id"], childColumns = ["problemId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = SymptomEntity::class, parentColumns = ["id"], childColumns = ["symptomId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class ProblemSymptomEntity(
    val problemId: String,
    val symptomId: String,
    val weight: Int = 1
)
