package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.ComponentEntity

@Dao
interface ComponentDao {
    @Query("SELECT * FROM components ORDER BY name ASC")
    fun getAll(): Flow<List<ComponentEntity>>

    @Query("SELECT * FROM components WHERE type = :type ORDER BY name ASC")
    fun getByType(type: String): Flow<List<ComponentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(components: List<ComponentEntity>)

    @Query("SELECT COUNT(*) FROM components")
    suspend fun count(): Int
}
