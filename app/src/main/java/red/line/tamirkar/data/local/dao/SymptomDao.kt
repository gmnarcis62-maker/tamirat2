package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.SymptomEntity

@Dao
interface SymptomDao {
    @Query("SELECT * FROM symptoms ORDER BY title ASC")
    fun getAll(): Flow<List<SymptomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(symptoms: List<SymptomEntity>)

    @Query("SELECT COUNT(*) FROM symptoms")
    suspend fun count(): Int
}
