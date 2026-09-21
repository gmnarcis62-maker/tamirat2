package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "components", indices = [Index("partNumber"), Index("type")])
data class ComponentEntity(
    @PrimaryKey val id: String,
    val partNumber: String? = null,
    val name: String,
    val nameEn: String,
    val type: String? = null,
    val manufacturer: String? = null,
    val function: String? = null,
    val packageType: String? = null,
    val datasheetUrl: String? = null,
    val description: String? = null
)
