package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.ProblemEntity
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity

@Dao
interface ProblemDao {
    @Query("SELECT * FROM problems")
    fun getAll(): Flow<List<ProblemEntity>>

    @Query("SELECT * FROM problems WHERE id = :id")
    suspend fun getById(id: String): ProblemEntity?

    @Query("SELECT * FROM problems WHERE category = :category")
    fun getByCategory(category: ProblemCategory): Flow<List<ProblemEntity>>

    @Query("SELECT * FROM problems WHERE severity = :severity")
    fun getBySeverity(severity: ProblemSeverity): Flow<List<ProblemEntity>>

    @Query("SELECT * FROM problems WHERE isCommon = 1")
    fun getCommon(): Flow<List<ProblemEntity>>

    @Query("SELECT * FROM problems WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<ProblemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problem: ProblemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(problems: List<ProblemEntity>)

    @Delete
    suspend fun delete(problem: ProblemEntity)

    @Query("DELETE FROM problems")
    suspend fun deleteAll()
}
