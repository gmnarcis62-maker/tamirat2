package red.line.tamirkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import red.line.tamirkar.ui.diagnosis.DiagnosisStartScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisWizardScreen
import red.line.tamirkar.ui.home.RotaryKnobScreen
import red.line.tamirkar.ui.problems.ProblemDetailScreen
import red.line.tamirkar.ui.problems.ProblemListScreen
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
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            RotaryKnobScreen(
                onMenuItemClick = { route ->
                    when (route) {
                        "diagnosis" -> navController.navigate("problem_list")
                        else -> navController.navigate("placeholder/$route")
                    }
                }
            )
        }

        composable("problem_list") {
            ProblemListScreen(
                onBackClick = { navController.popBackStack() },
                onProblemClick = { problemId ->
                    navController.navigate("problem_detail/$problemId")
                }
            )
        }

        composable(
            route = "problem_detail/{problemId}",
            arguments = listOf(navArgument("problemId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProblemDetailScreen(
                problemId = backStackEntry.arguments?.getString("problemId") ?: "",
                onBackClick = { navController.popBackStack() },
                onGuideClick = { /* TODO */ },
                onRelatedProblemClick = { relatedId ->
                    navController.navigate("problem_detail/$relatedId")
                }
            )
        }

        composable("diagnosis_start") {
            DiagnosisStartScreen(
                onBackClick = { navController.popBackStack() },
                onStartDiagnosis = { problemId, modelId ->
                    val route = if (modelId != null) {
                        "diagnosis_wizard/$problemId?modelId=$modelId"
                    } else {
                        "diagnosis_wizard/$problemId"
                    }
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = "diagnosis_wizard/{problemId}?modelId={modelId}",
            arguments = listOf(
                navArgument("problemId") { type = NavType.StringType },
                navArgument("modelId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            DiagnosisWizardScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = "placeholder/{route}",
            arguments = listOf(navArgument("route") { type = NavType.StringType })
        ) { backStackEntry ->
            PlaceholderScreen(
                route = backStackEntry.arguments?.getString("route") ?: "",
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun PlaceholderScreen(route: String, onBack: () -> Unit) {
    val routeLabel = when (route) {
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
            Text("بازگشت")
        }
    }
}