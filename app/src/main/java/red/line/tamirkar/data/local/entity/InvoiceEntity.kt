package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "invoices",
    foreignKeys = [ForeignKey(entity = RepairCaseEntity::class, parentColumns = ["id"], childColumns = ["repairCaseId"])],
    indices = [Index("repairCaseId"), Index("createdAt")]
)
data class InvoiceEntity(
    @PrimaryKey val id: String,
    val repairCaseId: String,
    val invoiceNumber: String,
    val totalAmount: Double,
    val discount: Double? = null,
    val finalAmount: Double,
    val isPaid: Boolean = false,
    val paymentMethod: String? = null,
    val pdfPath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
