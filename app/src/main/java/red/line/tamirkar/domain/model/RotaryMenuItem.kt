package red.line.tamirkar.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class RotaryMenuItem(
    val id: String,
    val label: String,
    val icon: ImageVector? = null,
    val angleDegrees: Float,
    val color: Color,
    val route: String
)
