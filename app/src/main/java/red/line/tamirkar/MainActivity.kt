package red.line.tamirkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import red.line.tamirkar.ui.home.RotaryKnobScreen
import red.line.tamirkar.ui.theme.TamirkarTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition { false }

        setContent {
            TamirkarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentRoute by remember { mutableStateOf<String?>(null) }

                    if (currentRoute == null) {
                        RotaryKnobScreen(
                            onMenuItemClick = { route ->
                                currentRoute = route
                            }
                        )
                    } else {
                        PlaceholderScreen(
                            route = currentRoute!!,
                            onBack = { currentRoute = null }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(route: String, onBack: () -> Unit) {
    val routeLabel = when (route) {
        "diagnosis" -> "عیب‌یابی هوشمند"
        "schematics" -> "نقشه‌خوانی"
        "power_diagnostics" -> "جریان‌کشی"
        "component_tester" -> "تست قطعات"
        "pinouts" -> "پین‌اوت‌ها"
        "secret_codes" -> "کدهای مخفی"
        else -> route
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = routeLabel,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "این صفحه به‌زودی اضافه می‌شود",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBack) {
            Text("بازگشت به منوی اصلی")
        }
    }
}