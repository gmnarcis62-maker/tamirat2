package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.TechnicianNoteEntity

@Dao
interface TechnicianNoteDao {
    @Query("SELECT * FROM technician_notes ORDER BY isPinned DESC, updatedAt DESC")
    fun getAll(): Flow<List<TechnicianNoteEntity>>

    @Query("SELECT * FROM technician_notes WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavorites(): Flow<List<TechnicianNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: TechnicianNoteEntity)

    @Query("SELECT COUNT(*) FROM technician_notes")
    suspend fun count(): Int
}
