package red.line.tamirkar.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "خانه", Icons.Default.Home)
    object Search : BottomNavItem("search", "جستجو", Icons.Default.Search)
    object Repairs : BottomNavItem("repairs", "تعمیرات", Icons.Default.Build)
    object Tools : BottomNavItem("tools", "ابزار", Icons.Default.Construction)
    object More : BottomNavItem("more", "بیشتر", Icons.Default.MoreHoriz)
}
