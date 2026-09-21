package red.line.tamirkar.data.local.dao

import androidx.room.*
import red.line.tamirkar.data.local.entity.DiagnosisOptionEntity

@Dao
interface DiagnosisOptionDao {
    @Query("SELECT * FROM diagnosis_options WHERE nodeId = :nodeId ORDER BY id ASC")
    suspend fun getByNodeId(nodeId: String): List<DiagnosisOptionEntity>

    @Query("SELECT * FROM diagnosis_options WHERE id = :id")
    suspend fun getById(id: String): DiagnosisOptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(options: List<DiagnosisOptionEntity>)

    @Query("SELECT COUNT(*) FROM diagnosis_options")
    suspend fun count(): Int
}
