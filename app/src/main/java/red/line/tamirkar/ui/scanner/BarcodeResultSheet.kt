package red.line.tamirkar.ui.scanner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import red.line.tamirkar.domain.model.BarcodeType
import red.line.tamirkar.domain.model.ScanResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeResultSheet(
    result: ScanResult.BarcodeResult,
    onDismiss: () -> Unit,
    viewModel: ScannerViewModel
) {
    val clipboardManager = LocalClipboardManager.current
    val isCopied by viewModel.isCopied.collectAsState()

    val typeColor = when (result.type) {
        BarcodeType.IMEI -> MaterialTheme.colorScheme.primary
        BarcodeType.SERIAL -> MaterialTheme.colorScheme.tertiary
        BarcodeType.URL -> Color(0xFF2196F3)
        BarcodeType.WIFI -> Color(0xFF4CAF50)
        BarcodeType.CONTACT_INFO -> Color(0xFFFF9800)
        BarcodeType.EMAIL -> Color(0xFFE91E63)
        else -> MaterialTheme.colorScheme.outline
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header with icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = typeColor.copy(alpha = 0.15f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when (result.type) {
                                    BarcodeType.IMEI -> Icons.Default.PermDeviceInformation
                                    BarcodeType.SERIAL -> Icons.Default.QrCode
                                    BarcodeType.URL -> Icons.Default.Link
                                    BarcodeType.WIFI -> Icons.Default.Wifi
                                    BarcodeType.CONTACT_INFO -> Icons.Default.ContactPhone
                                    BarcodeType.EMAIL -> Icons.Default.Email
                                    else -> Icons.Default.QrCodeScanner
                                },
                                contentDescription = null,
                                tint = typeColor,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "بارکد شناسایی شد",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "نوع: ${result.type.label} | فرمت: ${result.format.label}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Result value card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "مقدار خوانده شده:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = result.displayValue ?: result.rawValue,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "مقدار خام: ${result.rawValue}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(result.rawValue))
                        viewModel.markAsCopied()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isCopied) "کپی شد!" else "کپی")
                }

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Replay, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("اسکن مجدد")
                }
            }

            // Type-specific actions
            when (result.type) {
                BarcodeType.IMEI -> {
                    Spacer(modifier = Modifier.height(12.dp))
                    TypeSpecificActionCard(
                        icon = Icons.Default.PhoneAndroid,
                        title = "استفاده در تعمیر",
                        description = "این IMEI را می‌توانید در فرم تعمیر جدید وارد کنید.",
                        actionText = "ثبت در تعمیر",
                        onAction = { /* Navigate to repair form */ }
                    )
                }
                BarcodeType.URL -> {
                    Spacer(modifier = Modifier.height(12.dp))
                    TypeSpecificActionCard(
                        icon = Icons.Default.OpenInBrowser,
                        title = "باز کردن لینک",
                        description = result.rawValue,
                        actionText = "باز کردن",
                        onAction = { /* Open URL */ }
                    )
                }
                BarcodeType.WIFI -> {
                    Spacer(modifier = Modifier.height(12.dp))
                    TypeSpecificActionCard(
                        icon = Icons.Default.Wifi,
                        title = "اتصال به WiFi",
                        description = "اطلاعات شبکه شناسایی شد.",
                        actionText = "اتصال",
                        onAction = { /* Connect to WiFi */ }
                    )
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun TypeSpecificActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    actionText: String,
    onAction: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1
                    )
                }
            }
            TextButton(onClick = onAction) {
                Text(actionText)
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
