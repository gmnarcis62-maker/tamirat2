package red.line.tamirkar.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import red.line.tamirkar.core.util.PersianDateUtil

@Composable
fun HomeScreen(
    onBrandsClick: () -> Unit = {},
    onDiagnosisClick: () -> Unit = {},
    onModelsClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                "دستیار تعمیرکار موبایل",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "امروز: ${PersianDateUtil.nowShamsiDateTime()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("مدل گوشی، مشکل، کد خطا یا قطعه را جستجو کنید...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("دسترسی سریع", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        val quickAccess = listOf(
            Triple("عیب‌یابی هوشمند", Icons.Default.Psychology) to onDiagnosisClick,
            Triple("برندهای موبایل", Icons.Default.BrandingWatermark) to onBrandsClick,
            Triple("مدل‌های گوشی", Icons.Default.Smartphone) to { onModelsClick("") },
            Triple("بانک تعمیرات", Icons.Default.Handyman) to {},
            Triple("خطاها", Icons.Default.ErrorOutline) to {},
            Triple("ابزار تعمیرکار", Icons.Default.Construction) to {}
        )

        items(quickAccess.size) { index ->
            val (data, action) = quickAccess[index]
            val (title, icon) = data
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = action
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(title, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
