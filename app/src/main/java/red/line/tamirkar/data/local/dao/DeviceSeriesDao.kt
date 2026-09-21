package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.DeviceSeriesEntity

@Dao
interface DeviceSeriesDao {
    @Query("SELECT * FROM device_series WHERE brandId = :brandId ORDER BY name ASC")
    fun getByBrand(brandId: String): Flow<List<DeviceSeriesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<DeviceSeriesEntity>)

    @Query("SELECT COUNT(*) FROM device_series")
    suspend fun count(): Int
}
