package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "brands", indices = [Index(value = ["slug"], unique = true)])
data class BrandEntity(
    @PrimaryKey val id: String,
    val name: String,
    val normalizedName: String,
    val nameEn: String,
    val slug: String,
    val logo: String? = null,
    val website: String? = null,
    val description: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
