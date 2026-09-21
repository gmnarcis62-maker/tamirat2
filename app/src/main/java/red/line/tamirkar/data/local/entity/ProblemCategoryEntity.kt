package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "problem_categories", indices = [Index(value = ["slug"], unique = true)])
data class ProblemCategoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val titleEn: String,
    val slug: String,
    val iconName: String? = null,
    val sortOrder: Int = 0
)
