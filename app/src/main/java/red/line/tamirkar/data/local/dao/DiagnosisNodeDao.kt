package red.line.tamirkar.data.local.dao

import androidx.room.*
import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity

@Dao
interface DiagnosisNodeDao {

    @Query("SELECT * FROM diagnosis_nodes WHERE problemId = :problemId AND isStartNode = 1 LIMIT 1")
    suspend fun getStartNodeForProblem(problemId: String): DiagnosisNodeEntity?

    @Query("SELECT * FROM diagnosis_nodes WHERE isStartNode = 1 LIMIT 1")
    suspend fun getStartNode(): DiagnosisNodeEntity?

    @Query("SELECT * FROM diagnosis_nodes WHERE id = :id")
    suspend fun getById(id: String): DiagnosisNodeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(node: DiagnosisNodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nodes: List<DiagnosisNodeEntity>)

    @Query("DELETE FROM diagnosis_nodes")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM diagnosis_nodes")
    suspend fun count(): Int
}