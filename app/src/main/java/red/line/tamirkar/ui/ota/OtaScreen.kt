package red.line.tamirkar.ui.ota

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.DownloadProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtaScreen(
    onBackClick: () -> Unit,
    viewModel: OtaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.checkForUpdates()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("به‌روزرسانی محتوا") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                actions = {
                    val state = uiState as? OtaUiState.Success
                    if (state?.isRefreshing == true) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    } else {
                        IconButton(onClick = { viewModel.refresh() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "تازه‌سازی")
                        }
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
                is OtaUiState.Loading -> {
                    OtaLoading()
                }
                is OtaUiState.Success -> {
                    // Update banner
                    if (state.availableUpdates > 0) {
                        UpdateBanner(count = state.availableUpdates)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Status summary
                    OtaStatusSummary(packages = state.packages)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Package list
                    Text(
                        text = "پکیج‌های موجود (${state.packages.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    state.packages.forEach { pkg ->
                        val progress = downloadProgress[pkg.id]
                        OtaPackageCard(
                            pkg = pkg,
                            progress = progress,
                            onDownload = { viewModel.downloadPackage(pkg.id) },
                            onInstall = { viewModel.installPackage(pkg.id) },
                            onCancel = { viewModel.cancelDownload(pkg.id) },
                            onDelete = { viewModel.deletePackage(pkg.id) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                is OtaUiState.Error -> {
                    OtaError(
                        message = state.message,
                        onRetry = { viewModel.loadPackages() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun UpdateBanner(count: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.SystemUpdate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$count به‌روزرسانی موجود است",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "پکیج‌های جدید برای دانلود و نصب آماده هستند.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun OtaStatusSummary(packages: List<OtaPackage>) {
    val installed = packages.count { it.isInstalled }
    val available = packages.count { !it.isInstalled && it.downloadStatus == DownloadStatus.IDLE }
    val downloading = packages.count { it.downloadStatus == DownloadStatus.DOWNLOADING }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatusSummaryCard(
            value = installed.toString(),
            label = "نصب شده",
            icon = Icons.Default.DownloadDone,
            color = Color(0xFF4CAF50),
            modifier = Modifier.weight(1f)
        )
        StatusSummaryCard(
            value = available.toString(),
            label = "در دسترس",
            icon = Icons.Default.CloudDownload,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        StatusSummaryCard(
            value = downloading.toString(),
            label = "در حال دانلود",
            icon = Icons.Default.Downloading,
            color = Color(0xFFFF9800),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatusSummaryCard(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun OtaPackageCard(
    pkg: OtaPackage,
    progress: DownloadProgress?,
    onDownload: () -> Unit,
    onInstall: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when (pkg.packageType) {
                                    PackageType.BRANDS -> Icons.Default.PhoneAndroid
                                    PackageType.MODELS -> Icons.Default.Devices
                                    PackageType.PROBLEMS -> Icons.Default.ReportProblem
                                    PackageType.DIAGNOSIS -> Icons.Default.AccountTree
                                    PackageType.FULL -> Icons.Default.FolderZip
                                    PackageType.PATCH -> Icons.Default.SystemUpdate
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = pkg.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = pkg.packageType.label,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Status badge
                val status = progress?.status ?: pkg.downloadStatus
                val badgeColor = when (status) {
                    DownloadStatus.INSTALLED -> Color(0xFF4CAF50)
                    DownloadStatus.DOWNLOADING -> Color(0xFFFF9800)
                    DownloadStatus.DOWNLOADED -> MaterialTheme.colorScheme.primary
                    DownloadStatus.INSTALLING -> Color(0xFF2196F3)
                    DownloadStatus.FAILED -> MaterialTheme.colorScheme.error
                    DownloadStatus.CANCELLED -> MaterialTheme.colorScheme.outline
                    else -> MaterialTheme.colorScheme.outline
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = badgeColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = status.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = pkg.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))

            // Info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(icon = Icons.Default.Numbers, text = "نسخه ${pkg.versionName}")
                InfoChip(icon = Icons.Default.Storage, text = pkg.fileSizeFormatted)
                InfoChip(icon = Icons.Default.Dataset, text = "${pkg.recordCount} رکورد")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar for downloading
            AnimatedVisibility(visible = status == DownloadStatus.DOWNLOADING && progress != null) {
                Column {
                    LinearProgressIndicator(
                        progress = { progress?.progress ?: 0f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFFFF9800),
                        trackColor = Color(0xFFFF9800).copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${((progress?.progress ?: 0f) * 100).toInt()}% - ${progress?.bytesDownloaded?.div(1024)} / ${progress?.totalBytes?.div(1024)} KB",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (status) {
                    DownloadStatus.IDLE, DownloadStatus.CANCELLED, DownloadStatus.FAILED -> {
                        Button(
                            onClick = onDownload,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.CloudDownload, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("دانلود")
                        }
                    }
                    DownloadStatus.DOWNLOADING -> {
                        OutlinedButton(
                            onClick = onCancel,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Cancel, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("لغو")
                        }
                    }
                    DownloadStatus.DOWNLOADED -> {
                        Button(
                            onClick = onInstall,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.InstallDesktop, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("نصب")
                        }
                    }
                    DownloadStatus.INSTALLING -> {
                        OutlinedButton(
                            onClick = {},
                            enabled = false,
                            modifier = Modifier.weight(1f)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("در حال نصب...")
                        }
                    }
                    DownloadStatus.INSTALLED -> {
                        OutlinedButton(
                            onClick = onDelete,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حذف")
                        }
                    }
                    DownloadStatus.PENDING -> {}
                }
            }

            // Error message
            AnimatedVisibility(visible = status == DownloadStatus.FAILED && progress?.errorMessage != null) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Error,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = progress?.errorMessage ?: "خطا در دانلود",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun OtaLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "در حال بارگذاری پکیج‌ها...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun OtaError(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CloudOff,
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
