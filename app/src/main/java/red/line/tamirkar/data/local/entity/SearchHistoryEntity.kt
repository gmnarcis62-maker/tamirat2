package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "search_history", indices = [Index("createdAt")])
data class SearchHistoryEntity(
    @PrimaryKey val id: String,
    val query: String,
    val normalizedQuery: String,
    val resultCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
