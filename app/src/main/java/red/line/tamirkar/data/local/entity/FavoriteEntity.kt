package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorites", indices = [Index(value = ["itemType", "itemId"], unique = true)])
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val itemType: String,
    val itemId: String,
    val createdAt: Long = System.currentTimeMillis()
)
