package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "data_package_versions", indices = [Index(value = ["packageId"], unique = true)])
data class DataPackageVersionEntity(
    @PrimaryKey val id: String,
    val packageId: String,
    val version: String,
    val minAppVersion: String? = null,
    val language: String? = null,
    val recordCountsJson: String? = null,
    val importedAt: Long = System.currentTimeMillis(),
    val status: String = "IMPORTED"
)
