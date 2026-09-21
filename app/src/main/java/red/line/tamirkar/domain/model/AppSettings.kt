package red.line.tamirkar.domain.model

enum class AppTheme(val label: String) {
    SYSTEM("پیش‌فرض سیستم"),
    LIGHT("روشن"),
    DARK("تاریک")
}

enum class AppLanguage(val label: String) {
    PERSIAN("فارسی"),
    ENGLISH("English")
}

data class AppSettings(
    val theme: AppTheme = AppTheme.SYSTEM,
    val dynamicColorEnabled: Boolean = true,
    val biometricEnabled: Boolean = false,
    val appLockEnabled: Boolean = false,
    val appLockTimeoutSeconds: Int = 300,
    val autoBackupEnabled: Boolean = false,
    val backupFrequencyDays: Int = 7,
    val notificationsEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val hapticEnabled: Boolean = true,
    val showRepairNotifications: Boolean = true,
    val showLowStockNotifications: Boolean = true,
    val compactMode: Boolean = false
)
