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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var showBackupDialog by remember { mutableStateOf(false) }
    var showRestoreDialog by remember { mutableStateOf(false) }
    var backupProgress by remember { mutableStateOf(0f) }
    var isBackingUp by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("پشتیبان‌گیری و بازیابی") },
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
                is SettingsUiState.Success -> {
                    val settings = state.settings

                    // Backup icon header
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.extraLarge,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(100.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CloudUpload,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "پشتیبان‌گیری از داده‌ها",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "داده‌های رنامه شامل مشتریان، تعمیرات، انبار و فاکتورها قابل پشتیبان‌گیری و بازیابی هستند.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Auto Backup Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Schedule,
                        title = "پشتیبان خودکار"
                    )
                    SettingsCard {
                        SettingsSwitchItem(
                            icon = Icons.Default.CloudSync,
                            title = "پشتیبان خودکار",
                            subtitle = "تهیه نسخه پشتیبان به صورت خودکار",
                            checked = settings.autoBackupEnabled,
                            onCheckedChange = { viewModel.setAutoBackup(it) }
                        )

                        AnimatedVisibility(visible = settings.autoBackupEnabled) {
                            Column {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                var showFreqDialog by remember { mutableStateOf(false) }
                                SettingsListItem(
                                    icon = Icons.Default.Repeat,
                                    title = "فاصله پشتیبان‌گیری",
                                    subtitle = "هر ${settings.backupFrequencyDays} روز",
                                    onClick = { showFreqDialog = true }
                                )
                                if (showFreqDialog) {
                                    val freqs = listOf(
                                        1 to "هر روز",
                                        3 to "هر ۳ روز",
                                        7 to "هر هفته",
                                        14 to "هر ۲ هفته",
                                        30 to "هر ماه"
                                    )
                                    AlertDialog(
                                        onDismissRequest = { showFreqDialog = false },
                                        title = { Text("فاصله پشتیبان‌گیری") },
                                        text = {
                                            Column {
                                                freqs.forEach { (days, label) ->
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(vertical = 4.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        RadioButton(
                                                            selected = settings.backupFrequencyDays == days,
                                                            onClick = {
                                                                viewModel.setBackupFrequency(days)
                                                                showFreqDialog = false
                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(label)
                                                    }
                                                }
                                            }
                                        },
                                        confirmButton = {
                                            TextButton(onClick = { showFreqDialog = false }) {
                                                Text("بستن")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Manual Backup Section
                    SettingsSectionHeader(
                        icon = Icons.Default.CloudUpload,
                        title = "پشتیبان دستی"
                    )
                    SettingsCard {
                        ListItem(
                            headlineContent = { Text("ایجاد پشتیبان") },
                            supportingContent = { Text("ذخیره فایل پشتیبان رمزنگاری شده") },
                            leadingContent = {
                                Icon(
                                    Icons.Default.Backup,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
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
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        ListItem(
                            headlineContent = { Text("بازیابی پشتیبان") },
                            supportingContent = { Text("بازگردانی از فایل پشتیبان") },
                            leadingContent = {
                                Icon(
                                    Icons.Default.Restore,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
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
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        ListItem(
                            headlineContent = {
                                Text(
                                    "حذف همه داده‌ها",
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            supportingContent = { Text("پاک کردن دائمی همه داده‌ها") },
                            leadingContent = {
                                Icon(
                                    Icons.Default.DeleteForever,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            },
                            trailingContent = {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Backup Info
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "اطلاعات پشتیبان",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            BackupInfoRow(icon = Icons.Default.CalendarToday, label = "آخرین پشتیبان:", value = "هنوز تهیه نشده")
                            BackupInfoRow(icon = Icons.Default.Storage, label = "حجم دیتابیس:", value = "~۲ مگابایت")
                            BackupInfoRow(icon = Icons.Default.Lock, label = "رمزنگاری:", value = "SQLCipher AES-256")
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
                else -> {
                    SettingsLoading()
                }
            }
        }
    }
}

@Composable
fun BackupInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
