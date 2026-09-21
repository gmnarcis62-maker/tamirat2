package red.line.tamirkar.ui.navigation

sealed class Screen(val route: String, val label: String) {
    object Splash : Screen("splash", "Splash")
    object Login : Screen("login", "ورود")
    object Home : Screen("home", "صفحه اصلی")
    object Brands : Screen("brands", "برندها")
    object Models : Screen("models/{brandId}", "مدل‌ها") {
        fun createRoute(brandId: String) = "models/$brandId"
    }
    object Problems : Screen("problems", "مشکلات")
    object ProblemDetail : Screen("problem/{problemId}", "جزئیات مشکل") {
        fun createRoute(problemId: String) = "problem/$problemId"
    }
    object RepairGuide : Screen("guide/{problemId}", "راهنمای تعمیر") {
        fun createRoute(problemId: String) = "guide/$problemId"
    }
    object Diagnosis : Screen("diagnosis", "عیب‌یابی هوشمند")
    object Customers : Screen("customers", "مشتریان")
    object RepairJobs : Screen("repair_jobs", "تعمیرات")
    object Inventory : Screen("inventory", "انبار")
    object Invoices : Screen("invoices", "فاکتورها")
    object Scanner : Screen("scanner", "اسکنر")
    object Reports : Screen("reports", "گزارش‌ها")
    object Settings : Screen("settings", "تنظیمات")
    object Backup : Screen("backup", "پشتیبان‌گیری")
    object Ota : Screen("ota", "به‌روزرسانی محتوا")
    object About : Screen("about", "درباره ما")

    // Rotary menu screens
    object Schematics : Screen("schematics", "نقشه‌خوانی")
    object PowerDiagnostics : Screen("power_diagnostics", "جریان‌کشی")
    object ComponentTester : Screen("component_tester", "تست قطعات")
    object Pinouts : Screen("pinouts", "پین‌اوت‌ها")
    object SecretCodes : Screen("secret_codes", "کدهای مخفی")
    object Troubleshooting : Screen("troubleshooting", "عیب‌یابی")
}
