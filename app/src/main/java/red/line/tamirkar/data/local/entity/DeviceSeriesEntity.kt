package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "device_series",
    foreignKeys = [ForeignKey(entity = BrandEntity::class, parentColumns = ["id"], childColumns = ["brandId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("brandId"), Index("slug")]
)
data class DeviceSeriesEntity(
    @PrimaryKey val id: String,
    val brandId: String,
    val name: String,
    val nameEn: String,
    val slug: String,
    val description: String? = null
)
