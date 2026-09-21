package red.line.tamirkar.ui.settings

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLockScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("امنیت و قفل برنامه") },
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

                    // Security icon header
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
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "تنظیمات امنیتی",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "با فعال‌سازی قفل بیومتریک، دسترسی به برنامه محافظت می‌شود.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Biometric Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Fingerprint,
                        title = "بیومتریک"
                    )
                    SettingsCard {
                        SettingsSwitchItem(
                            icon = Icons.Default.Fingerprint,
                            title = "ورود با اثر انگشت",
                            subtitle = "باز کردن برنامه با اثر انگشت یا چهره",
                            checked = settings.biometricEnabled,
                            onCheckedChange = { viewModel.setBiometric(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // App Lock Section
                    SettingsSectionHeader(
                        icon = Icons.Default.Lock,
                        title = "قفل برنامه"
                    )
                    SettingsCard {
                        SettingsSwitchItem(
                            icon = Icons.Default.Lock,
                            title = "قفل خودکار",
                            subtitle = "قفل برنامه هنگام خروج",
                            checked = settings.appLockEnabled,
                            onCheckedChange = { viewModel.setAppLock(it) }
                        )

                        AnimatedVisibility(visible = settings.appLockEnabled) {
                            Column {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                var showTimeoutDialog by remember { mutableStateOf(false) }
                                SettingsListItem(
                                    icon = Icons.Default.Timer,
                                    title = "زمان قفل خودکار",
                                    subtitle = formatTimeoutSec(settings.appLockTimeoutSeconds),
                                    onClick = { showTimeoutDialog = true }
                                )
                                if (showTimeoutDialog) {
                                    val timeouts = listOf(
                                        60 to "۱ دقیقه",
                                        180 to "۳ دقیقه",
                                        300 to "۵ دقیقه",
                                        600 to "۱۰ دقیقه",
                                        900 to "۱۵ دقیقه"
                                    )
                                    AlertDialog(
                                        onDismissRequest = { showTimeoutDialog = false },
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
                                                            selected = settings.appLockTimeoutSeconds == seconds,
                                                            onClick = {
                                                                viewModel.setAppLockTimeout(seconds)
                                                                showTimeoutDialog = false
                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(label)
                                                    }
                                                }
                                            }
                                        },
                                        confirmButton = {
                                            TextButton(onClick = { showTimeoutDialog = false }) {
                                                Text("بستن")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Info Card
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "نکات امنیتی",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "• داده‌های برنامه با SQLCipher رمزنگاری شده‌اند.\n• قفل بیومتریک از سخت‌افزار دستگاه استفاده می‌کند.\n• در صورت تغییر اثر انگشت در تنظیمات دستگاه، قفل غیرفعال می‌شود.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.9f)
                                )
                            }
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

private fun formatTimeoutSec(seconds: Int): String {
    return when (seconds) {
        60 -> "۱ دقیقه"
        180 -> "۳ دقیقه"
        300 -> "۵ دقیقه"
        600 -> "۱۰ دقیقه"
        900 -> "۱۵ دقیقه"
        else -> "$seconds ثانیه"
    }
}
