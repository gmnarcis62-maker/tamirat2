package red.line.tamirkar.ui.widget

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import red.line.tamirkar.domain.model.RepairStatus
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetDataProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: RepairRepository
) {
    suspend fun getTodayRepairCount(): Int {
        return try {
            val repairs = repository.getAllRepairJobs()
            val today = System.currentTimeMillis()
            val startOfDay = today - (today % (24 * 60 * 60 * 1000))
            repairs.count { it.receivedAt >= startOfDay }
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getPendingCount(): Int {
        return try {
            val repairs = repository.getAllRepairJobs()
            repairs.count {
                it.status == RepairStatus.RECEIVED ||
                it.status == RepairStatus.IN_PROGRESS ||
                it.status == RepairStatus.DIAGNOSING ||
                it.status == RepairStatus.WAITING_PARTS
            }
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getCompletedCount(): Int {
        return try {
            val repairs = repository.getAllRepairJobs()
            repairs.count {
                it.status == RepairStatus.COMPLETED ||
                it.status == RepairStatus.DELIVERED
            }
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getTotalRevenue(): Double {
        return try {
            val invoices = repository.getAllInvoices()
            invoices.sumOf { it.finalAmount }
        } catch (e: Exception) {
            0.0
        }
    }
}
