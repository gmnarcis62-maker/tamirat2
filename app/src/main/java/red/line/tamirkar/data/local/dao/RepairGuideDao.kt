package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.RepairGuideEntity

@Dao
interface RepairGuideDao {
    @Query("SELECT * FROM repair_guides")
    fun getAll(): Flow<List<RepairGuideEntity>>

    @Query("SELECT * FROM repair_guides WHERE problemId = :problemId")
    suspend fun getByProblemId(problemId: String): RepairGuideEntity?

    @Query("SELECT * FROM repair_guides WHERE id = :id")
    suspend fun getById(id: String): RepairGuideEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(guide: RepairGuideEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(guides: List<RepairGuideEntity>)

    @Delete
    suspend fun delete(guide: RepairGuideEntity)

    @Query("DELETE FROM repair_guides")
    suspend fun deleteAll()
}
