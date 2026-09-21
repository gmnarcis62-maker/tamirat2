package red.line.tamirkar.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.data.local.entity.InvoiceEntity

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM invoices ORDER BY createdAt DESC")
    fun getAll(): Flow<List<InvoiceEntity>>

    @Query("SELECT * FROM invoices ORDER BY createdAt DESC")
    suspend fun getAllSync(): List<InvoiceEntity>

    @Query("SELECT * FROM invoices WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): InvoiceEntity?

    @Query("SELECT * FROM invoices WHERE isPaid = 1 ORDER BY createdAt DESC")
    suspend fun getPaid(): List<InvoiceEntity>

    @Query("SELECT * FROM invoices WHERE isPaid = 0 ORDER BY createdAt DESC")
    suspend fun getUnpaid(): List<InvoiceEntity>

    @Query("SELECT * FROM invoices WHERE invoiceNumber LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    suspend fun search(query: String): List<InvoiceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(invoice: InvoiceEntity)

    @Query("DELETE FROM invoices WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM invoices")
    suspend fun count(): Int
}
