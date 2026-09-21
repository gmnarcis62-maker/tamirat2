package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.SynonymEntity

@Dao
interface SynonymDao {
    @Query("SELECT * FROM synonyms WHERE normalizedTerm = :term LIMIT 1")
    suspend fun getByTerm(term: String): SynonymEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(synonyms: List<SynonymEntity>)

    @Query("SELECT COUNT(*) FROM synonyms")
    suspend fun count(): Int
}
