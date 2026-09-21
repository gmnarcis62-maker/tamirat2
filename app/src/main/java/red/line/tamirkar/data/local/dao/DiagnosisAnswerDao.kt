package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.DiagnosisAnswerEntity

@Dao
interface DiagnosisAnswerDao {
    @Query("SELECT * FROM diagnosis_answers WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getBySession(sessionId: String): Flow<List<DiagnosisAnswerEntity>>

    @Query("SELECT * FROM diagnosis_answers WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    suspend fun getBySessionSync(sessionId: String): List<DiagnosisAnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: DiagnosisAnswerEntity)

    @Query("DELETE FROM diagnosis_answers WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM diagnosis_answers WHERE sessionId = :sessionId")
    suspend fun deleteBySession(sessionId: String)

    @Query("SELECT COUNT(*) FROM diagnosis_answers")
    suspend fun count(): Int
}
