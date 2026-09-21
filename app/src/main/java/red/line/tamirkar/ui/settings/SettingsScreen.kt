package red.line.tamirkar.ui.settings

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import red.line.tamirkar.domain.model.AppSettings
import red.line.tamirkar.domain.model.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var showResetDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLockTimeoutDialog by remember { mutableStateOf(false) }
    var showBackupFreqDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تنظیمات") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is SettingsUiState.Loading -> {
                    SettingsLoading()
                }
                is SettingsUiState.Success -> {
                    val settings = state.settings

                    // Appearance Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Palette,
                        title = "ظاهر"
                    )
                    SettingsCard {
                        // Theme
                        SettingsListItem(
                            icon = Icons.Default.DarkMode,
                            title = "تم برنامه",
                            subtitle = settings.theme.label,
                            onClick = { showThemeDialog = true }
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))

                        // Dynamic Color
                        SettingsSwitchItem(
                            icon = Icons.Default.ColorLens,
                            title = "رنگ دینامیک",
                            subtitle = "استفاده از پالت رنگی دستگاه",
                            checked = settings.dynamicColorEnabled,
                            onCheckedChange = { viewModel.setDynamicColor(it) }
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))

                        // Compact Mode
                        SettingsSwitchItem(
                            icon = Icons.Default.ViewCompact,
                            title = "حالت فشرده",
                            subtitle = "نمایش فشرده‌تر لیست‌ها",
                            checked = settings.compactMode,
                            onCheckedChange = { viewModel.setCompactMode(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Security Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Security,
                        title = "امنیت"
                    )
                    SettingsCard {
                        // Biometric
                        SettingsSwitchItem(
                            icon = Icons.Default.Fingerprint,
                            title = "ورود با اثر انگشت",
                            subtitle = "باز کردن برنامه با بیومتریک",
                            checked = settings.biometricEnabled,
                            onCheckedChange = { viewModel.setBiometric(it) }
                        )
                        AnimatedVisibility(visible = settings.biometricEnabled) {
                            Column {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsSwitchItem(
                                    icon = Icons.Default.Lock,
                                    title = "قفل برنامه",
                                    subtitle = "قفل خودکار پس از خروج از برنامه",
                                    checked = settings.appLockEnabled,
                                    onCheckedChange = { viewModel.setAppLock(it) }
                                )
                            }
                        }
                        AnimatedVisibility(visible = settings.appLockEnabled && settings.biometricEnabled) {
                            Column {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsListItem(
                                    icon = Icons.Default.Timer,
                                    title = "زمان قفل خودکار",
                                    subtitle = formatTimeout(settings.appLockTimeoutSeconds),
                                    onClick = { showLockTimeoutDialog = true }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Notifications Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Notifications,
                        title = "اعلانات"
                    )
                    SettingsCard {
                        // Master switch
                        SettingsSwitchItem(
                            icon = Icons.Default.NotificationsActive,
                            title = "اعلانات",
                            subtitle = "فعال‌سازی همه اعلانات",
                            checked = settings.notificationsEnabled,
                            onCheckedChange = { viewModel.setNotifications(it) }
                        )

                        AnimatedVisibility(visible = settings.notificationsEnabled) {
                            Column {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsSwitchItem(
                                    icon = Icons.Default.VolumeUp,
                                    title = "صدا",
                                    subtitle = "پخش صدای اعلان",
                                    checked = settings.soundEnabled,
                                    onCheckedChange = { viewModel.setSound(it) }
                                )
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsSwitchItem(
                                    icon = Icons.Default.Vibration,
                                    title = "لرزش",
                                    subtitle = "لرزش هنگام اعلان",
                                    checked = settings.hapticEnabled,
                                    onCheckedChange = { viewModel.setHaptic(it) }
                                )
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsSwitchItem(
                                    icon = Icons.Default.BuildCircle,
                                    title = "اعلان تعمیرات",
                                    subtitle = "اطلاع‌رسانی تغییر وضعیت تعمیر",
                                    checked = settings.showRepairNotifications,
                                    onCheckedChange = { viewModel.setRepairNotifications(it) }
                                )
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsSwitchItem(
                                    icon = Icons.Default.Inventory,
                                    title = "اعلان موجودی کم",
                                    subtitle = "هشدار کمبود قطعات انبار",
                                    checked = settings.showLowStockNotifications,
                                    onCheckedChange = { viewModel.setLowStockNotifications(it) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Backup Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Backup,
                        title = "پشتیبان‌گیری"
                    )
                    SettingsCard {
                        SettingsSwitchItem(
                            icon = Icons.Default.CloudUpload,
                            title = "پشتیبان خودکار",
                            subtitle = "تهیه نسخه پشتیبان خودکار",
                            checked = settings.autoBackupEnabled,
                            onCheckedChange = { viewModel.setAutoBackup(it) }
                        )
                        AnimatedVisibility(visible = settings.autoBackupEnabled) {
                            Column {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                SettingsListItem(
                                    icon = Icons.Default.Schedule,
                                    title = "فاصله پشتیبان‌گیری",
                                    subtitle = "هر ${settings.backupFrequencyDays} روز",
                                    onClick = { showBackupFreqDialog = true }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Data Management Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Storage,
                        title = "مدیریت داده"
                    )
                    SettingsCard {
                        SettingsActionItem(
                            icon = Icons.Default.DeleteSweep,
                            title = "پاک کردن تاریخچه جستجو",
                            subtitle = "حذف سوابق جستجوهای اخیر",
                            iconTint = MaterialTheme.colorScheme.error,
                            onClick = { /* Clear search history */ }
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsActionItem(
                            icon = Icons.Default.RestartAlt,
                            title = "بازگشت به تنظیمات پیش‌فرض",
                            subtitle = "همه تنظیمات به حالت اولیه بازمی‌گردد",
                            iconTint = MaterialTheme.colorScheme.error,
                            onClick = { showResetDialog = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // About Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Info,
                        title = "درباره"
                    )
                    SettingsCard {
                        SettingsInfoItem(
                            icon = Icons.Default.AppShortcut,
                            title = "نسخه برنامه",
                            value = "1.0.0"
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsInfoItem(
                            icon = Icons.Default.Code,
                            title = "توسعه‌دهنده",
                            value = "ردلاین سافت البرز"
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsInfoItem(
                            icon = Icons.Default.Storage,
                            title = "نسخه دیتابیس",
                            value = "1"
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
                is SettingsUiState.Error -> {
                    SettingsError(
                        message = state.message,
                        onRetry = { /* Refresh */ }
                    )
                }
            }
        }
    }

    // Theme Selection Dialog
    if (showThemeDialog) {
        val currentSettings = (uiState as? SettingsUiState.Success)?.settings
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("انتخاب تم") },
            text = {
                Column {
                    AppTheme.entries.forEach { theme ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentSettings?.theme == theme,
                                onClick = {
                                    viewModel.setTheme(theme)
                                    showThemeDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(theme.label)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("بستن")
                }
            }
        )
    }

    // Lock Timeout Dialog
    if (showLockTimeoutDialog) {
        val timeouts = listOf(60 to "۱ دقیقه", 180 to "۳ دقیقه", 300 to "۵ دقیقه", 600 to "۱۰ دقیقه", 900 to "۱۵ دقیقه")
        val currentTimeout = (uiState as? SettingsUiState.Success)?.settings?.appLockTimeoutSeconds ?: 300
        AlertDialog(
            onDismissRequest = { showLockTimeoutDialog = false },
            title = { Text("زمان قفل خودکار") },
            text = {
                Column {
                    timeouts.forEach { (seconds, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentTimeout == seconds,
                                onClick = {
                                    viewModel.setAppLockTimeout(seconds)
                                    showLockTimeoutDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(label)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLockTimeoutDialog = false }) {
                    Text("بستن")
                }
            }
        )
    }

    // Backup Frequency Dialog
    if (showBackupFreqDialog) {
        val frequencies = listOf(1 to "هر روز", 3 to "هر ۳ روز", 7 to "هر هفته", 14 to "هر ۲ هفته", 30 to "هر ماه")
        val currentFreq = (uiState as? SettingsUiState.Success)?.settings?.backupFrequencyDays ?: 7
        AlertDialog(
            onDismissRequest = { showBackupFreqDialog = false },
            title = { Text("فاصله پشتیبان‌گیری") },
            text = {
                Column {
                    frequencies.forEach { (days, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentFreq == days,
                                onClick = {
                                    viewModel.setBackupFrequency(days)
                                    showBackupFreqDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(label)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showBackupFreqDialog = false }) {
                    Text("بستن")
                }
            }
        )
    }

    // Reset Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("بازگشت به تنظیمات پیش‌فرض") },
            text = { Text("آیا از بازگشت همه تنظیمات به حالت اولیه اطمینان دارید؟ این عمل قابل بازگشت نیست.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetToDefaults()
                        showResetDialog = false
                    }
                ) {
                    Text("بازگشت", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("انصراف")
                }
            }
        )
    }
}

@Composable
fun SettingsSectionHeader(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
fun SettingsListItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.bodyLarge) },
        supportingContent = { Text(subtitle, style = MaterialTheme.typography.bodySmall) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.outline
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp)
    )
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.bodyLarge) },
        supportingContent = { Text(subtitle, style = MaterialTheme.typography.bodySmall) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp)
    )
}

@Composable
fun SettingsActionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                color = iconTint
            )
        },
        supportingContent = { Text(subtitle, style = MaterialTheme.typography.bodySmall) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.outline
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(Modifier),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp)
    )
}

@Composable
fun SettingsInfoItem(
    icon: ImageVector,
    title: String,
    value: String
) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.bodyLarge) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp)
    )
}

private fun formatTimeout(seconds: Int): String {
    return when (seconds) {
        60 -> "۱ دقیقه"
        180 -> "۳ دقیقه"
        300 -> "۵ دقیقه"
        600 -> "۱۰ دقیقه"
        900 -> "۱۵ دقیقه"
        else -> "$seconds ثانیه"
    }
}

@Composable
fun SettingsLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "در حال بارگذاری تنظیمات...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun SettingsError(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Icon(Icons.Default.Replay, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("تلاش مجدد")
        }
    }
}
