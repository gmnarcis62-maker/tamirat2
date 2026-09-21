package red.line.tamirkar.ui.pinouts

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinoutsScreen(onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    var selectedConnector by remember { mutableStateOf<String?>(null) }

    val connectors = listOf(
        Triple("سوکت شارژ USB-C", "۱۲۴ پین", Icons.Default.Usb),
        Triple("سوکت شارژ Lightning", "۸ پین", Icons.Default.ChargingStation),
        Triple("سوکت LCD FPC", "۳۰-۴۰ پین", Icons.Default.ScreenRotation),
        Triple("سوکت باتری", "۲-۴ پین", Icons.Default.BatteryFull),
        Triple("آنتن اصلی", "کاکسیال", Icons.Default.SignalCellularAlt),
        Triple("SIM Tray", "۶-۸ پین", Icons.Default.SimCard),
        Triple("کلیدهای صدا", "۴ پین", Icons.Default.VolumeUp),
        Triple("موتور ویبره", "۲ پین", Icons.Default.Vibration)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("پین‌اوت‌ها (Pinouts)") },
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
                        Icons.Default.SettingsEthernet,
                        null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "جدول پین‌اوت‌ها",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "مشخصات پین‌های سوکت‌ها و کانکتورهای رایج",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            connectors.forEach { (name, pins, icon) ->
                val isSelected = selectedConnector == name
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedConnector = name },
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            icon,
                            null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = pins,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Icon(
                            if (isSelected) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            null,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                if (isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))
                    PinoutDetailCard(name = name)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PinoutDetailCard(name: String) {
    val pinData = when (name) {
        "سوکت شارژ USB-C" -> listOf(
            "پین A6, B6: D+ (USB 2.0)",
            "پین A7, B7: D- (USB 2.0)",
            "پین A8: VBUS (۵V)",
            "پین A1, B12: GND",
            "پین B8: SBU1", "پین A5: CC (Config Channel)",
            "پین A4, B9: VBUS (High Power)"
        )
        "سوکت شارژ Lightning" -> listOf(
            "پین ۱: GND", "پین ۲: L0p (Data+)",
            "پین ۳: L0n (Data-)", "پین ۴: ID/VCC",
            "پین ۵: GND", "پین ۶: VBUS",
            "پین ۷: L1n", "پین ۸: L1p"
        )
        "سوکت LCD FPC" -> listOf(
            "پین ۱-۶: MIPI DSI Data Lane",
            "پین ۷-۸: VDDIO (۱.۸V)",
            "پین ۹-۱۰: AVDD (۲.۸-۳.۳V)",
            "پین ۱۱: RESET", "پین ۱۲: TE (Tearing Effect)",
            "پین ۱۳-۱۴: GND"
        )
        "سوکت باتری" -> listOf(
            "پین ۱: BAT+ (positive)",
            "پین ۲: BAT- (negative/GND)",
            "پین ۳: thermistor (NTC)",
            "پین ۴: BSI (Battery Size Info)"
        )
        else -> listOf("اطلاعات این کانکتور به زودی اضافه خواهد شد.")
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "مشخصات پین $name",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            pinData.forEach { pin ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(4.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = pin,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
