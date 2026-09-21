package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.DeviceModelEntity

@Dao
interface DeviceModelDao {
    @Query("SELECT * FROM device_models ORDER BY name ASC")
    fun getAll(): Flow<List<DeviceModelEntity>>

    @Query("SELECT * FROM device_models ORDER BY name ASC")
    suspend fun getAllSync(): List<DeviceModelEntity>

    @Query("SELECT * FROM device_models WHERE brandId = :brandId ORDER BY name ASC")
    fun getByBrand(brandId: String): Flow<List<DeviceModelEntity>>

    @Query("SELECT * FROM device_models WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DeviceModelEntity?

    @Query("SELECT * FROM device_models WHERE normalizedName LIKE '%' || :query || '%' OR modelNumber LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun search(query: String): List<DeviceModelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(models: List<DeviceModelEntity>)

    @Query("SELECT COUNT(*) FROM device_models")
    suspend fun count(): Int
}
