package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "error_codes",
    foreignKeys = [
        ForeignKey(entity = BrandEntity::class, parentColumns = ["id"], childColumns = ["brandId"]),
        ForeignKey(entity = DeviceModelEntity::class, parentColumns = ["id"], childColumns = ["deviceModelId"])
    ],
    indices = [Index("brandId"), Index("deviceModelId"), Index("code")]
)
data class ErrorCodeEntity(
    @PrimaryKey val id: String,
    val brandId: String,
    val deviceModelId: String? = null,
    val code: String,
    val message: String? = null,
    val category: String? = null,
    val description: String? = null,
    val possibleCauses: String? = null,
    val solutions: String? = null,
    val severity: String? = null
)
