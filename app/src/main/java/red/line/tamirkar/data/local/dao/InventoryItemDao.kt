package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.InventoryItemEntity

@Dao
interface InventoryItemDao {
    @Query("SELECT * FROM inventory_items ORDER BY name ASC")
    fun getAll(): Flow<List<InventoryItemEntity>>

    @Query("SELECT * FROM inventory_items ORDER BY name ASC")
    suspend fun getAllSync(): List<InventoryItemEntity>

    @Query("SELECT * FROM inventory_items WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): InventoryItemEntity?

    @Query("SELECT * FROM inventory_items WHERE category = :category ORDER BY name ASC")
    suspend fun getByCategory(category: String): List<InventoryItemEntity>

    @Query("SELECT * FROM inventory_items WHERE quantity <= minQuantity AND minQuantity > 0 ORDER BY quantity ASC")
    suspend fun getLowStock(): List<InventoryItemEntity>

    @Query("SELECT * FROM inventory_items WHERE name LIKE '%' || :query || '%' OR partNumber LIKE '%' || :query || '%' OR supplier LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun search(query: String): List<InventoryItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: InventoryItemEntity)

    @Query("DELETE FROM inventory_items WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM inventory_items")
    suspend fun count(): Int
}
