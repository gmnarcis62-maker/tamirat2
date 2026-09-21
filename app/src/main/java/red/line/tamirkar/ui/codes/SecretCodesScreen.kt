package red.line.tamirkar.ui.codes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecretCodesScreen(onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var copiedCode by remember { mutableStateOf<String?>(null) }

    val universalCodes = listOf(
        Triple("*#06#", "نمایش IMEI", Icons.Default.Dialpad),
        Triple("*#*#4636#*#*", "اطلاعات گوشی (Testing Menu)", Icons.Default.PhoneAndroid),
        Triple("*#*#225#*#*", "تقویم و رویدادها", Icons.Default.CalendarToday),
        Triple("*#*#426#*#*", "تنظیمات Google Play Services", Icons.Default.SettingsApplications),
        Triple("*#*#759#*#*", "RLZ Debug UI", Icons.Default.BugReport),
        Triple("*#*#34971539#*#*", "اطلاعات دوربین", Icons.Default.CameraAlt),
        Triple("*#*#232339#*#*", "تست WiFi", Icons.Default.Wifi),
        Triple("*#*#232331#*#*", "تست Bluetooth", Icons.Default.Bluetooth),
        Triple("*#*#2664#*#*", "تست تاچ", Icons.Default.TouchApp),
        Triple("*#*#0842#*#*", "تست لرزش و نور صفحه", Icons.Default.Vibration),
        Triple("*#*#0589#*#*", "تست سنسور نور", Icons.Default.WbSunny),
        Triple("*#*#726262#*#*", "فرمت OTP", Icons.Default.DeleteForever)
    )

    val samsungCodes = listOf(
        Triple("*#0*#", "حالت تست سخت‌افزار", Icons.Default.Build),
        Triple("*#1234#", "ورژن فریمور (PDA, CSC, MODEM)", Icons.Default.SystemUpdate),
        Triple("*#12580*369#", "اطلاعات سخت‌افزار و نرم‌افزار", Icons.Default.Memory),
        Triple("*#9900#", "SysDump Debug", Icons.Default.BugReport),
        Triple("*#0011#", "Service Mode (Network)", Icons.Default.SignalCellularAlt),
        Triple("*#0228#", "وضعیت باتری", Icons.Default.BatteryFull),
        Triple("*#0808#", "تنظیمات USB", Icons.Default.Usb)
    )

    val xiaomiCodes = listOf(
        Triple("*#*#6484#*#*", "CIT (Hardware Test)", Icons.Default.Build),
        Triple("*#*#4636#*#*", "اطلاعات گوشی", Icons.Default.PhoneAndroid),
        Triple("*#*#76937#*#*", "تست رنگ صفحه", Icons.Default.Palette),
        Triple("*#*#6485#*#*", "وضعیت باتری (MB_06)", Icons.Default.BatteryFull)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("کدهای مخفی (Secret Codes)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
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

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Code,
                        null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "کدهای سرویس و تست",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "کدهای USSD مخفی برای تست سخت‌افزار و سرویس",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            CodeSection(
                title = "کدهای عمومی اندروید",
                codes = universalCodes,
                copiedCode = copiedCode,
                onCopy = { code ->
                    copyToClipboard(context, code)
                    copiedCode = code
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            CodeSection(
                title = "کدهای سامسونگ",
                codes = samsungCodes,
                copiedCode = copiedCode,
                onCopy = { code ->
                    copyToClipboard(context, code)
                    copiedCode = code
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            CodeSection(
                title = "کدهای شیائومی",
                codes = xiaomiCodes,
                copiedCode = copiedCode,
                onCopy = { code ->
                    copyToClipboard(context, code)
                    copiedCode = code
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CodeSection(
    title: String,
    codes: List<Triple<String, String, androidx.compose.ui.graphics.vector.ImageVector>>,
    copiedCode: String?,
    onCopy: (String) -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(10.dp))

    codes.forEach { (code, desc, icon) ->
        val isCopied = copiedCode == code
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = code,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                IconButton(onClick = { onCopy(code) }) {
                    Icon(
                        if (isCopied) Icons.Default.CheckCircle else Icons.Default.ContentCopy,
                        null,
                        tint = if (isCopied) androidx.compose.ui.graphics.Color(0xFF4CAF50)
                        else MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Secret Code", text)
    clipboard.setPrimaryClip(clip)
}
