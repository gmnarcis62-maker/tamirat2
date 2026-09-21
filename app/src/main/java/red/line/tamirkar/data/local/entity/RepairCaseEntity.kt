package red.line.tamirkar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "repair_cases",
    foreignKeys = [
        ForeignKey(entity = CustomerEntity::class, parentColumns = ["id"], childColumns = ["customerId"]),
        ForeignKey(entity = DeviceModelEntity::class, parentColumns = ["id"], childColumns = ["deviceModelId"])
    ],
    indices = [Index("customerId"), Index("deviceModelId"), Index("status"), Index("receivedAt")]
)
data class RepairCaseEntity(
    @PrimaryKey val id: String,
    val customerId: String,
    val deviceModelId: String? = null,
    val customerDeviceId: String? = null,
    val reportedProblem: String,
    val technicianDiagnosis: String? = null,
    val status: String,
    val totalCost: Double? = null,
    val partsCost: Double? = null,
    val laborCost: Double? = null,
    val receivedAt: Long = System.currentTimeMillis(),
    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val deliveredAt: Long? = null,
    val beforePhotos: String? = null,
    val afterPhotos: String? = null,
    val voiceNotePath: String? = null,
    val notes: String? = null
)
