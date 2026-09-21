package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.ProblemCategoryEntity

@Dao
interface ProblemCategoryDao {
    @Query("SELECT * FROM problem_categories ORDER BY sortOrder ASC")
    fun getAll(): Flow<List<ProblemCategoryEntity>>

    @Query("SELECT * FROM problem_categories WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProblemCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<ProblemCategoryEntity>)

    @Query("SELECT COUNT(*) FROM problem_categories")
    suspend fun count(): Int
}
