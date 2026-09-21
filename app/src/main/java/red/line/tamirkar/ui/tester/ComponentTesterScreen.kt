package red.line.tamirkar.ui.tester

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
fun ComponentTesterScreen(onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    var selectedComponent by remember { mutableStateOf<String?>(null) }

    val components = listOf(
        Triple("خازن SMD", "تست ESR و ظرفیت", Icons.Default.FlashOn),
        Triple("دیود SMD", "تست جهت و ولتاژ", Icons.Default.CompareArrows),
        Triple("مقاومت", "تست مقدار اهمی", Icons.Default.Straighten),
        Triple("ترانزیستور", "تست gain و پین‌اوت", Icons.Default.SettingsInputComponent),
        Triple("IC تغذیه", "تست ولتاژهای خروجی", Icons.Default.Power),
        Triple("کویل", "تست پیوستگی", Icons.Default.SyncAlt),
        Triple("باتری", "تست ولتاژ و ظرفیت", Icons.Default.BatteryFull),
        Triple("LCD", "تست پیکسل و تاچ", Icons.Default.ScreenLockPortrait)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تست قطعات (Component Tester)") },
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
                        Icons.Default.Memory,
                        null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "تست قطعات SMD",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "راهنمای تست و تشخیص سلامت قطعات",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            components.chunked(2).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEach { (name, desc, icon) ->
                        val isSelected = selectedComponent == name
                        ElevatedCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .clickable { selectedComponent = name },
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    icon,
                                    null,
                                    modifier = Modifier.size(32.dp),
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (row.size < 2) Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            selectedComponent?.let { name ->
                Spacer(modifier = Modifier.height(20.dp))
                ComponentTestGuide(name = name)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ComponentTestGuide(name: String) {
    val guide = when (name) {
        "خازن SMD" -> listOf(
            "مولتی‌متر را روی حالت خازن بگذارید",
            "پین‌های مثبت و منفی را به ترمینال‌ها وصل کنید",
            "مقدار ظرفیت باید در محدوده تلورانس باشد",
            "ESR باید کمتر از ۱ اهم باشد (برای خازن‌های بزرگ)",
            "اگر short است: خازن خراب است"
        )
        "دیود SMD" -> listOf(
            "مولتی‌متر را روی حالت دیود بگذارید",
            "پروب قرمز به آند، سیاه به کاتد: باید ۰.۵-۰.۷V نشان دهد",
            "برعکس: باید OL (Open) باشد",
            "اگر هر دو طرف short است: دیود سوخته",
            "اگر هر دو طرف OL است: دیود باز است"
        )
        "IC تغذیه" -> listOf(
            "دیتاشیت IC را پیدا کنید",
            "پین‌های ورودی (VBAT, VBUS) را تست کنید",
            "پین‌های خروجی (۱.۸V, ۰.۸V, ۳.۳V) را با اسکپ بررسی کنید",
            "اگر ورودی دارید اما خروجی ندارید: IC خراب است",
            "اگر IC داغ می‌شود: اتصالی در خروجی وجود دارد"
        )
        else -> listOf("راهنمای تست این قطعه به زودی اضافه خواهد شد.")
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "راهنمای تست $name",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            guide.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
