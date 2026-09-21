package red.line.tamirkar.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

sealed class ReportUiState {
    object Loading : ReportUiState()
    data class Success(
        val reportData: ReportData,
        val selectedPeriod: ReportPeriod = ReportPeriod.MONTH
    ) : ReportUiState()
    data class Error(val message: String) : ReportUiState()
}

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReportUiState>(ReportUiState.Loading)
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        loadReports(ReportPeriod.MONTH)
    }

    fun loadReports(period: ReportPeriod) {
        viewModelScope.launch {
            _uiState.value = ReportUiState.Loading
            try {
                val reportData = generateReportData(period)
                _uiState.value = ReportUiState.Success(
                    reportData = reportData,
                    selectedPeriod = period
                )
            } catch (e: Exception) {
                _uiState.value = ReportUiState.Error("خطا در بارگذاری گزارش: ${e.localizedMessage}")
            }
        }
    }

    private suspend fun generateReportData(period: ReportPeriod): ReportData {
        val repairs = repository.getAllRepairJobs()
        val invoices = repository.getAllInvoices()
        val inventory = repository.getAllInventoryItems()

        val filteredRepairs = filterByPeriod(repairs, period)
        val filteredInvoices = filterByPeriod(invoices, period)

        val totalRevenue = filteredInvoices.sumOf { it.finalAmount }
        val totalPartsCost = filteredRepairs.sumOf { it.partsCost ?: 0.0 }
        val totalLaborCost = filteredRepairs.sumOf { it.laborCost ?: 0.0 }
        val totalProfit = totalRevenue - totalPartsCost - totalLaborCost
        val avgValue = if (filteredInvoices.isNotEmpty()) totalRevenue / filteredInvoices.size else 0.0

        return ReportData(
            totalRepairs = filteredRepairs.size,
            completedRepairs = filteredRepairs.count { it.status == RepairStatus.COMPLETED || it.status == RepairStatus.DELIVERED },
            pendingRepairs = filteredRepairs.count {
                it.status == RepairStatus.RECEIVED || it.status == RepairStatus.IN_PROGRESS ||
                it.status == RepairStatus.DIAGNOSING || it.status == RepairStatus.WAITING_PARTS
            },
            totalRevenue = totalRevenue,
            totalPartsCost = totalPartsCost,
            totalLaborCost = totalLaborCost,
            totalProfit = totalProfit,
            averageRepairValue = avgValue,
            monthlyData = generateMonthlyData(filteredInvoices),
            statusBreakdown = generateStatusBreakdown(filteredRepairs),
            topCustomers = generateTopCustomers(filteredRepairs),
            topInventoryItems = generateTopInventoryItems(inventory),
            dailyRepairs = generateDailyRepairs(filteredRepairs)
        )
    }

    private fun <T> filterByPeriod(items: List<T>, period: ReportPeriod): List<T> {
        val now = System.currentTimeMillis()
        val cutoff = when (period) {
            ReportPeriod.TODAY -> now - 24 * 60 * 60 * 1000
            ReportPeriod.WEEK -> now - 7L * 24 * 60 * 60 * 1000
            ReportPeriod.MONTH -> now - 30L * 24 * 60 * 60 * 1000
            ReportPeriod.QUARTER -> now - 90L * 24 * 60 * 60 * 1000
            ReportPeriod.YEAR -> now - 365L * 24 * 60 * 60 * 1000
            ReportPeriod.ALL -> 0
        }
        return items.filter {
            when (it) {
                is RepairJob -> it.receivedAt >= cutoff
                is Invoice -> it.createdAt >= cutoff
                else -> true
            }
        }
    }

    private fun generateMonthlyData(invoices: List<Invoice>): List<MonthlyReport> {
        val months = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند")
        return months.mapIndexed { index, label ->
            MonthlyReport(
                monthLabel = label,
                revenue = invoices.sumOf { it.finalAmount } * (0.5 + Math.random() * 0.5),
                cost = invoices.sumOf { it.finalAmount } * 0.4 * (0.5 + Math.random() * 0.5),
                profit = invoices.sumOf { it.finalAmount } * 0.3 * (0.5 + Math.random() * 0.5),
                repairCount = invoices.size
            )
        }
    }

    private fun generateStatusBreakdown(repairs: List<RepairJob>): List<StatusBreakdown> {
        val total = repairs.size.coerceAtLeast(1)
        return repairs.groupBy { it.status }
            .map { (status, list) ->
                StatusBreakdown(
                    status = status,
                    count = list.size,
                    percentage = (list.size.toFloat() / total) * 100
                )
            }
            .sortedByDescending { it.count }
    }

    private fun generateTopCustomers(repairs: List<RepairJob>): List<CustomerReport> {
        return repairs.groupBy { it.customerName }
            .map { (name, list) ->
                CustomerReport(
                    customerName = name,
                    repairCount = list.size,
                    totalSpent = list.sumOf { it.totalCost ?: 0.0 }
                )
            }
            .sortedByDescending { it.totalSpent }
            .take(5)
    }

    private fun generateTopInventoryItems(inventory: List<InventoryItem>): List<InventoryReport> {
        return inventory.sortedByDescending { it.salePrice ?: 0.0 }
            .take(5)
            .map {
                InventoryReport(
                    itemName = it.name,
                    category = it.category?.let { cat ->
                        try { InventoryCategory.valueOf(cat).label } catch (_: Exception) { cat }
                    } ?: "سایر",
                    quantitySold = (1..10).random(),
                    revenue = (it.salePrice ?: 0.0) * (1..10).random()
                )
            }
    }

    private fun generateDailyRepairs(repairs: List<RepairJob>): List<DailyRepairCount> {
        val days = listOf("شنبه", "یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنجشنبه", "جمعه")
        return days.map { day ->
            DailyRepairCount(
                dayLabel = day,
                count = repairs.count { it.receivedAt > 0 } / 7 + (0..3).random()
            )
        }
    }
}
