package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.DiagnosisSessionEntity

@Dao
interface DiagnosisSessionDao {
    @Query("SELECT * FROM diagnosis_sessions WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DiagnosisSessionEntity?

    @Query("SELECT * FROM diagnosis_sessions WHERE status = :status ORDER BY startedAt DESC")
    fun getByStatus(status: String): Flow<List<DiagnosisSessionEntity>>

    @Query("SELECT * FROM diagnosis_sessions ORDER BY startedAt DESC")
    fun getAll(): Flow<List<DiagnosisSessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: DiagnosisSessionEntity)

    @Update
    suspend fun update(session: DiagnosisSessionEntity)

    @Delete
    suspend fun delete(session: DiagnosisSessionEntity)

    @Query("SELECT COUNT(*) FROM diagnosis_sessions")
    suspend fun count(): Int

    @Query("DELETE FROM diagnosis_sessions WHERE startedAt < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)
}
