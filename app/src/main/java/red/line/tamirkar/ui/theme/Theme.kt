package red.line.tamirkar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFD32F2F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFCDD2),
    onPrimaryContainer = Color(0xFF7F0000),
    secondary = Color(0xFF546E7A),
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1A1A1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    error = Color(0xFFB71C1C),
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFEF5350),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF8B0000),
    onPrimaryContainer = Color(0xFFFFCDD2),
    secondary = Color(0xFF90A4AE),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color(0xFFECEFF1),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFECEFF1),
    error = Color(0xFFEF5350),
    onError = Color.White
)

@Composable
fun TamirkarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}