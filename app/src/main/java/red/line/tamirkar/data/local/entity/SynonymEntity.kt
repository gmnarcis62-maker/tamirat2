package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "synonyms", indices = [Index("canonicalTerm"), Index("normalizedTerm")])
data class SynonymEntity(
    @PrimaryKey val id: String,
    val term: String,
    val normalizedTerm: String,
    val canonicalTerm: String,
    val language: String = "fa",
    val groupId: String
)
