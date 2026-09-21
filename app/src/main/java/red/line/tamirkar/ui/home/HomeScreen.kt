package red.line.tamirkar.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrandingWatermark
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import red.line.tamirkar.core.util.PersianDateUtil

data class QuickAccessItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

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
            QuickAccessItem("عیب‌یابی هوشمند", Icons.Default.Psychology, onDiagnosisClick),
            QuickAccessItem("برندهای موبایل", Icons.Default.BrandingWatermark, onBrandsClick),
            QuickAccessItem("مدل‌های گوشی", Icons.Default.Smartphone) { onModelsClick("") },
            QuickAccessItem("بانک تعمیرات", Icons.Default.Handyman) {},
            QuickAccessItem("خطاها", Icons.Default.ErrorOutline) {},
            QuickAccessItem("ابزار تعمیرکار", Icons.Default.Construction) {}
        )

        items(quickAccess) { item ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = item.onClick
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(item.title, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}