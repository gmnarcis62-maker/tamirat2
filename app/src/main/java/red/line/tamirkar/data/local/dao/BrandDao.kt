package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.BrandEntity

@Dao
interface BrandDao {
    @Query("SELECT * FROM brands WHERE isActive = 1 ORDER BY name ASC")
    fun getAll(): Flow<List<BrandEntity>>

    @Query("SELECT * FROM brands WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): BrandEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(brands: List<BrandEntity>)

    @Query("SELECT COUNT(*) FROM brands")
    suspend fun count(): Int
}
