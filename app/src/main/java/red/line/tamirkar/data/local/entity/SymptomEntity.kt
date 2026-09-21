package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms", indices = [Index("title")])
data class SymptomEntity(
    @PrimaryKey val id: String,
    val title: String,
    val titleEn: String,
    val description: String? = null
)
