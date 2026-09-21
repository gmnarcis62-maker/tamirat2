package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items", indices = [Index("partNumber"), Index("category")])
data class InventoryItemEntity(
    @PrimaryKey val id: String,
    val partNumber: String? = null,
    val name: String,
    val category: String? = null,
    val quantity: Int = 0,
    val minQuantity: Int = 0,
    val purchasePrice: Double? = null,
    val salePrice: Double? = null,
    val supplier: String? = null,
    val location: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
