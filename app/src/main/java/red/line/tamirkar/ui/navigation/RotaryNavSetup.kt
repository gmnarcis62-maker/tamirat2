package red.line.tamirkar.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import red.line.tamirkar.ui.codes.SecretCodesScreen
import red.line.tamirkar.ui.home.RotaryKnobScreen
import red.line.tamirkar.ui.pinouts.PinoutsScreen
import red.line.tamirkar.ui.power.PowerDiagnosticsScreen
import red.line.tamirkar.ui.problems.DiagnosisScreen
import red.line.tamirkar.ui.schematics.SchematicsScreen
import red.line.tamirkar.ui.tester.ComponentTesterScreen

fun NavGraphBuilder.addRotaryRoutes(navController: NavHostController) {
    composable(Screen.Home.route) {
        RotaryKnobScreen(
            onMenuItemClick = { route ->
                when (route) {
                    "schematics" -> navController.navigate(Screen.Schematics.route)
                    "power_diagnostics" -> navController.navigate(Screen.PowerDiagnostics.route)
                    "component_tester" -> navController.navigate(Screen.ComponentTester.route)
                    "pinouts" -> navController.navigate(Screen.Pinouts.route)
                    "secret_codes" -> navController.navigate(Screen.SecretCodes.route)
                    "diagnosis" -> navController.navigate(Screen.Diagnosis.route)
                    else -> navController.navigate(route)
                }
            }
        )
    }

    composable(Screen.Schematics.route) {
        SchematicsScreen(onBackClick = { navController.popBackStack() })
    }

    composable(Screen.PowerDiagnostics.route) {
        PowerDiagnosticsScreen(onBackClick = { navController.popBackStack() })
    }

    composable(Screen.ComponentTester.route) {
        ComponentTesterScreen(onBackClick = { navController.popBackStack() })
    }

    composable(Screen.Pinouts.route) {
        PinoutsScreen(onBackClick = { navController.popBackStack() })
    }

    composable(Screen.SecretCodes.route) {
        SecretCodesScreen(onBackClick = { navController.popBackStack() })
    }

    composable(Screen.Diagnosis.route) {
        DiagnosisScreen(
            onBackClick = { navController.popBackStack() },
            onProblemFound = { problemId ->
                navController.navigate(Screen.ProblemDetail.createRoute(problemId))
            }
        )
    }
}
