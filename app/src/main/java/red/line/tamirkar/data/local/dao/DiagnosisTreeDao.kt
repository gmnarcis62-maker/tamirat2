package red.line.tamirkar.data.local.dao

import androidx.room.*
import red.line.tamirkar.data.local.entity.DiagnosisTreeEntity

@Dao
interface DiagnosisTreeDao {
    @Query("SELECT * FROM diagnosis_trees WHERE problemId = :problemId AND (modelId IS NULL OR modelId = :modelId) LIMIT 1")
    suspend fun getTreeForProblem(problemId: String, modelId: String?): DiagnosisTreeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trees: List<DiagnosisTreeEntity>)

    @Query("SELECT * FROM diagnosis_trees WHERE id = :id")
    suspend fun getById(id: String): DiagnosisTreeEntity?

    @Query("SELECT COUNT(*) FROM diagnosis_trees")
    suspend fun count(): Int
}
