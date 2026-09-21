package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.ErrorCodeEntity

@Dao
interface ErrorCodeDao {
    @Query("SELECT * FROM error_codes WHERE brandId = :brandId ORDER BY code ASC")
    fun getByBrand(brandId: String): Flow<List<ErrorCodeEntity>>

    @Query("SELECT * FROM error_codes WHERE code = :code LIMIT 1")
    suspend fun getByCode(code: String): ErrorCodeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(errorCodes: List<ErrorCodeEntity>)

    @Query("SELECT COUNT(*) FROM error_codes")
    suspend fun count(): Int
}
