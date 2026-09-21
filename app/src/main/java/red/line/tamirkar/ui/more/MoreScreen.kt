package red.line.tamirkar.ui.more

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import red.line.tamirkar.core.util.PersianDateUtil

@Composable
fun MoreScreen(
    onAboutClick: () -> Unit = {},
    onCustomersClick: () -> Unit = {},
    onRepairJobsClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            "بیشتر",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "تاریخ: ${PersianDateUtil.nowShamsiDate()}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Management Section
        Text(
            "مدیریت",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        val managementItems = listOf(
            Triple("مشتریان", Icons.Default.People) to onCustomersClick,
            Triple("تعمیرات", Icons.Default.Handyman) to onRepairJobsClick,
            Triple("انبار قطعات", Icons.Default.Inventory) to {},
            Triple("فاکتورها", Icons.Default.Receipt) to {},
            Triple("گزارش‌ها", Icons.Default.Assessment) to {}
        )

        managementItems.forEach { (data, action) ->
            val (title, icon) = data
            MoreItemCard(title = title, icon = icon, onClick = action)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tools Section
        Text(
            "ابزارها",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        val toolItems = listOf(
            Triple("اسکنر بارکد", Icons.Default.QrCodeScanner) to {},
            Triple("تبدیل واحد", Icons.Default.SwapHoriz) to {},
            Triple("ماشین‌حساب", Icons.Default.Calculate) to {},
            Triple("یادداشت تکنسین", Icons.Default.NoteAlt) to {}
        )

        toolItems.forEach { (data, action) ->
            val (title, icon) = data
            MoreItemCard(title = title, icon = icon, onClick = action)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Settings Section
        Text(
            "تنظیمات و اطلاعات",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        val settingItems = listOf(
            Triple("تنظیمات", Icons.Default.Settings) to {},
            Triple("پشتیبان‌گیری", Icons.Default.Backup) to {},
            Triple("درباره ما", Icons.Default.Info) to onAboutClick
        )

        settingItems.forEach { (data, action) ->
            val (title, icon) = data
            MoreItemCard(title = title, icon = icon, onClick = action)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun MoreItemCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
