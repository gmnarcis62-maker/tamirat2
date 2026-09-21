package red.line.tamirkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import red.line.tamirkar.ui.navigation.RepairApp
import red.line.tamirkar.ui.theme.TamirkarTheme
import red.line.tamirkar.ui.widget.AppShortcutHelper

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AppShortcutHelper.createShortcuts(this)

        splashScreen.setKeepOnScreenCondition { false }

        setContent {
            TamirkarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RepairApp()
                }
            }
        }
    }
}
