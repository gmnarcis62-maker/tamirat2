package red.line.tamirkar.ui.power

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PowerDiagnosticsScreen(onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    var voltage by remember { mutableStateOf("") }
    var current by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جریان‌کشی (Power Diagnostics)") },
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
                        Icons.Default.Bolt,
                        null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "تشخیص جریان‌کشی",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "با ورود مقایر منبع تغذیه، علت خاموشی را پیدا کنید",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = voltage,
                onValueChange = { voltage = it },
                label = { Text("ولتاژ (V)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = { Icon(Icons.Default.ElectricBolt, null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = current,
                onValueChange = { current = it },
                label = { Text("جریان (mA)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = { Icon(Icons.Default.Speed, null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showResult = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Search, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("تشخیص")
            }

            if (showResult) {
                Spacer(modifier = Modifier.height(20.dp))
                PowerDiagnosisResult(
                    voltage = voltage.toFloatOrNull() ?: 0f,
                    current = current.toFloatOrNull() ?: 0f
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PowerDiagnosisResult(voltage: Float, current: Float) {
    val (title, color, suggestions) = when {
        voltage == 0f || current == 0f -> Triple(
            "مقادیر نامعتبر",
            Color.Gray,
            listOf("ولتاژ و جریان را وارد کنید")
        )
        current < 10 -> Triple(
            "عدم مصرف (Open Circuit)",
            Color(0xFFFF9800),
            listOf(
                "مسیر VBAT از باتری تا IC PMU قطع است",
                "IC تغذیه (PMU) خراب است",
                "کلید پاور کار نمی‌کند",
                "برد آبخورده و مسیر خورده است"
            )
        )
        current in 10f..50f -> Triple(
            "مصرف نرمال اولیه",
            Color(0xFF4CAF50),
            listOf(
                "گوشی در حال بوت شدن است",
                "اگر روی این مقدار می‌ماند: مشکل در eMMC یا CPU است",
                "اگر spike می‌خورد و reboot می‌شود: مشکل تغذیه است"
            )
        )
        current in 50f..200f -> Triple(
            "مصرف بالا (Partial Short)",
            Color(0xFFFF5722),
            listOf(
                "اتصالی جزئی در برد وجود دارد",
                "IC تغذیه داغ می‌شود",
                "کاپاسیتور خراب است",
                "با تزریق ولتاژ نقطه داغ را پیدا کنید"
            )
        )
        current > 200 -> Triple(
            "اتصالی کامل (Dead Short)",
            Color(0xFFF44336),
            listOf(
                "اتصالی مستقیم VCC به GND",
                "IC تغذیه کاملاً سوخته است",
                "خازن یا کویل short شده است",
                "با روغن یا الکل نقطه داغ را پیدا کنید"
            )
        )
        else -> Triple("نامشخص", Color.Gray, emptyList())
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Assessment, null, tint = color)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            suggestions.forEach { suggestion ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        color = color,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                    Text(
                        text = suggestion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
