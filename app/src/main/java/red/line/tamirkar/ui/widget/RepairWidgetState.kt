package red.line.tamirkar.ui.widget

data class RepairWidgetState(
    val todayRepairs: Int = 0,
    val pendingRepairs: Int = 0,
    val completedRepairs: Int = 0,
    val totalRevenue: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)
