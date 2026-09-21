package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.DataPackageVersionEntity

@Dao
interface DataPackageVersionDao {
    @Query("SELECT * FROM data_package_versions ORDER BY importedAt DESC")
    fun getAll(): Flow<List<DataPackageVersionEntity>>

    @Query("SELECT * FROM data_package_versions WHERE packageId = :packageId LIMIT 1")
    suspend fun getByPackageId(packageId: String): DataPackageVersionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(version: DataPackageVersionEntity)

    @Query("SELECT COUNT(*) FROM data_package_versions")
    suspend fun count(): Int
}
