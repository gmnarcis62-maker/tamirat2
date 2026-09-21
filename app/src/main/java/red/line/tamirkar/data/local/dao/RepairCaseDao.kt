package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.RepairCaseEntity

@Dao
interface RepairCaseDao {
    @Query("SELECT * FROM repair_cases ORDER BY receivedAt DESC")
    fun getAll(): Flow<List<RepairCaseEntity>>

    @Query("SELECT * FROM repair_cases ORDER BY receivedAt DESC")
    suspend fun getAllSync(): List<RepairCaseEntity>

    @Query("SELECT * FROM repair_cases WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): RepairCaseEntity?

    @Query("SELECT * FROM repair_cases WHERE status = :status ORDER BY receivedAt DESC")
    fun getByStatus(status: String): Flow<List<RepairCaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repairCase: RepairCaseEntity)

    @Query("DELETE FROM repair_cases WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM repair_cases")
    suspend fun count(): Int
}
