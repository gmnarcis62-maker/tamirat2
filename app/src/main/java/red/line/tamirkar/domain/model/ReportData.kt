package red.line.tamirkar.domain.model

data class ReportData(
    val totalRepairs: Int = 0,
    val completedRepairs: Int = 0,
    val pendingRepairs: Int = 0,
    val totalRevenue: Double = 0.0,
    val totalPartsCost: Double = 0.0,
    val totalLaborCost: Double = 0.0,
    val totalProfit: Double = 0.0,
    val averageRepairValue: Double = 0.0,
    val monthlyData: List<MonthlyReport> = emptyList(),
    val statusBreakdown: List<StatusBreakdown> = emptyList(),
    val topCustomers: List<CustomerReport> = emptyList(),
    val topInventoryItems: List<InventoryReport> = emptyList(),
    val dailyRepairs: List<DailyRepairCount> = emptyList()
)

data class MonthlyReport(
    val monthLabel: String,
    val revenue: Double,
    val cost: Double,
    val profit: Double,
    val repairCount: Int
)

data class StatusBreakdown(
    val status: RepairStatus,
    val count: Int,
    val percentage: Float
)

data class CustomerReport(
    val customerName: String,
    val repairCount: Int,
    val totalSpent: Double
)

data class InventoryReport(
    val itemName: String,
    val category: String,
    val quantitySold: Int,
    val revenue: Double
)

data class DailyRepairCount(
    val dayLabel: String,
    val count: Int
)

enum class ReportPeriod(val label: String) {
    TODAY("امروز"),
    WEEK("هفته اخیر"),
    MONTH("ماه اخیر"),
    QUARTER("سه ماهه"),
    YEAR("سال اخیر"),
    ALL("همه")
}
