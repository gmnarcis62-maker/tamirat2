package red.line.tamirkar.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import red.line.tamirkar.ui.about.AboutScreen
import red.line.tamirkar.ui.brands.BrandsScreen
import red.line.tamirkar.ui.customer.AddEditCustomerScreen
import red.line.tamirkar.ui.customer.CustomerScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisStartScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisWizardScreen
import red.line.tamirkar.ui.home.HomeScreen
import red.line.tamirkar.ui.models.ModelsScreen
import red.line.tamirkar.ui.more.MoreScreen
import red.line.tamirkar.ui.problems.ProblemDetailScreen
import red.line.tamirkar.ui.problems.RepairGuideScreen
import red.line.tamirkar.ui.repair.AddEditRepairJobScreen
import red.line.tamirkar.ui.repair.RepairJobScreen
import red.line.tamirkar.ui.repair.RepairsScreen
import red.line.tamirkar.ui.search.SearchScreen
import red.line.tamirkar.ui.tools.ToolsScreen

@Composable
fun RepairApp(
    navController: NavHostController = rememberNavController(),
    onBackPressed: () -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Repairs,
        BottomNavItem.Tools,
        BottomNavItem.More
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val bottomNavRoutes = items.map { it.route }
            if (currentRoute in bottomNavRoutes) {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onBrandsClick = { navController.navigate(Screen.Brands.route) },
                    onDiagnosisClick = { navController.navigate(Screen.DiagnosisStart.route) },
                    onModelsClick = { navController.navigate(Screen.Brands.route) }
                )
            }
            composable(Screen.Brands.route) {
                BrandsScreen(
                    onBrandClick = { brandId ->
                        navController.navigate(Screen.Models.createRoute(brandId))
                    }
                )
            }
            composable(
                Screen.Models.route,
                arguments = listOf(navArgument("brandId") { type = NavType.StringType })
            ) {
                ModelsScreen(
                    onModelClick = { modelId ->
                        // Will navigate to ModelDetails in future
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.DiagnosisStart.route) {
                DiagnosisStartScreen(
                    onBackClick = { navController.popBackStack() },
                    onStartDiagnosis = { problemId, modelId ->
                        navController.navigate(Screen.DiagnosisWizard.createRoute(problemId, modelId))
                    }
                )
            }
            composable(
                Screen.DiagnosisWizard.route,
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
                Screen.ProblemDetail.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) {
                ProblemDetailScreen(
                    onBackClick = { navController.popBackStack() },
                    onStartDiagnosis = { problemId ->
                        navController.navigate(Screen.DiagnosisWizard.createRoute(problemId))
                    },
                    onStartRepairGuide = { problemId ->
                        navController.navigate(Screen.RepairGuide.createRoute(problemId))
                    }
                )
            }
            composable(
                Screen.RepairGuide.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) {
                RepairGuideScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.Customers.route) {
                CustomerScreen(
                    onBackClick = { navController.popBackStack() },
                    onAddCustomer = { navController.navigate(Screen.AddEditCustomer.createRoute()) },
                    onCustomerClick = { customerId ->
                        navController.navigate(Screen.AddEditCustomer.createRoute(customerId))
                    }
                )
            }
            composable(
                Screen.AddEditCustomer.route,
                arguments = listOf(
                    navArgument("customerId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                AddEditCustomerScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.RepairJobs.route) {
                RepairJobScreen(
                    onBackClick = { navController.popBackStack() },
                    onAddRepair = { navController.navigate(Screen.AddEditRepairJob.createRoute()) },
                    onRepairClick = { repairId ->
                        navController.navigate(Screen.AddEditRepairJob.createRoute(repairId))
                    }
                )
            }
            composable(
                Screen.AddEditRepairJob.route,
                arguments = listOf(
                    navArgument("repairId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                AddEditRepairJobScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(BottomNavItem.Search.route) { SearchScreen() }
            composable(BottomNavItem.Repairs.route) {
                RepairsScreen(
                    onCustomersClick = { navController.navigate(Screen.Customers.route) },
                    onRepairJobsClick = { navController.navigate(Screen.RepairJobs.route) },
                    onInventoryClick = { /* TODO */ },
                    onInvoicesClick = { /* TODO */ }
                )
            }
            composable(BottomNavItem.Tools.route) { ToolsScreen() }
            composable(BottomNavItem.More.route) {
                MoreScreen(
                    onAboutClick = { navController.navigate(Screen.About.route) },
                    onCustomersClick = { navController.navigate(Screen.Customers.route) },
                    onRepairJobsClick = { navController.navigate(Screen.RepairJobs.route) }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}
