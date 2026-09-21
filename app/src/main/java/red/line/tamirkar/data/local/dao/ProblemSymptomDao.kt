package red.line.tamirkar.data.local.dao

import androidx.room.*
import red.line.tamirkar.data.local.entity.ProblemSymptomEntity

@Dao
interface ProblemSymptomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(links: List<ProblemSymptomEntity>)

    @Query("SELECT COUNT(*) FROM problem_symptoms")
    suspend fun count(): Int
}
