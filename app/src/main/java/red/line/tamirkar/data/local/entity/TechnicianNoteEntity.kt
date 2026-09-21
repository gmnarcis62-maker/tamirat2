package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "technician_notes",
    foreignKeys = [
        ForeignKey(entity = DeviceModelEntity::class, parentColumns = ["id"], childColumns = ["deviceModelId"]),
        ForeignKey(entity = ProblemEntity::class, parentColumns = ["id"], childColumns = ["problemId"])
    ],
    indices = [Index("deviceModelId"), Index("problemId"), Index("isPinned")]
)
data class TechnicianNoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val deviceModelId: String? = null,
    val problemId: String? = null,
    val category: String? = null,
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
