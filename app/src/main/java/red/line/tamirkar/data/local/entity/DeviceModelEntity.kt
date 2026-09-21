package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "device_models",
    foreignKeys = [
        ForeignKey(entity = BrandEntity::class, parentColumns = ["id"], childColumns = ["brandId"]),
        ForeignKey(entity = DeviceSeriesEntity::class, parentColumns = ["id"], childColumns = ["seriesId"])
    ],
    indices = [Index("brandId"), Index("seriesId"), Index("modelNumber"), Index("normalizedName")]
)
data class DeviceModelEntity(
    @PrimaryKey val id: String,
    val brandId: String,
    val seriesId: String? = null,
    val name: String,
    val nameEn: String,
    val modelNumber: String,
    val normalizedName: String,
    val codename: String? = null,
    val releaseYear: Int? = null,
    val chipset: String? = null,
    val cpu: String? = null,
    val gpu: String? = null,
    val ramVariants: String? = null,
    val storageVariants: String? = null,
    val displayType: String? = null,
    val displaySize: String? = null,
    val batteryCapacity: Int? = null,
    val chargingType: String? = null,
    val os: String? = null,
    val androidVersion: String? = null,
    val storageType: String? = null,
    val networkType: String? = null,
    val description: String? = null
)
