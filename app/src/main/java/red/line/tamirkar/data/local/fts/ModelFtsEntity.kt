package red.line.tamirkar.data.local.fts

import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "model_fts")
@Fts4(contentEntity = red.line.tamirkar.data.local.entity.DeviceModelEntity::class)
data class ModelFtsEntity(
    val name: String,
    val nameEn: String,
    val modelNumber: String,
    val normalizedName: String
)
