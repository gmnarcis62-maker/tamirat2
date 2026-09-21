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
import red.line.tamirkar.ui.backup.BackupScreen
import red.line.tamirkar.ui.brands.BrandsScreen
import red.line.tamirkar.ui.codes.SecretCodesScreen
import red.line.tamirkar.ui.customer.CustomerScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisStartScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisWizardScreen
import red.line.tamirkar.ui.home.RotaryKnobScreen
import red.line.tamirkar.ui.models.ModelsScreen
import red.line.tamirkar.ui.power.PowerDiagnosticsScreen
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
                        "problem_list" -> navController.navigate("problem_list")
                        "power_diagnostics" -> navController.navigate("power_diagnostics")
                        "secret_codes" -> navController.navigate("secret_codes")
                        "backup" -> navController.navigate("backup")
                        "customer" -> navController.navigate("customer")
                        "brands" -> navController.navigate("brands")
                        "models" -> navController.navigate("models")
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

        composable("power_diagnostics") {
            PowerDiagnosticsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("secret_codes") {
            SecretCodesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("backup") {
            BackupScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("customer") {
            CustomerScreen(
                onBackClick = { navController.popBackStack() },
                onAddCustomer = { /* TODO */ },
                onCustomerClick = { /* TODO */ }
            )
        }

        composable("brands") {
            BrandsScreen(
                onBackClick = { navController.popBackStack() },
                onBrandClick = { brandId ->
                    navController.navigate("models_by_brand/$brandId")
                }
            )
        }

        composable(
            route = "models_by_brand/{brandId}",
            arguments = listOf(navArgument("brandId") { type = NavType.StringType })
        ) {
            ModelsScreen(
                onBackClick = { navController.popBackStack() },
                onModelClick = { /* TODO */ }
            )
        }

        composable("models") {
            ModelsScreen(
                onBackClick = { navController.popBackStack() },
                onModelClick = { /* TODO */ }
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
        "component_tester" -> "تست قطعات"
        "pinouts" -> "پین‌اوت‌ها"
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