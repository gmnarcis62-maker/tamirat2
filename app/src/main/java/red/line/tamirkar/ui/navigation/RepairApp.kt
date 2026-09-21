package red.line.tamirkar.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import red.line.tamirkar.ui.about.AboutScreen
import red.line.tamirkar.ui.backup.BackupScreen
import red.line.tamirkar.ui.brands.BrandsScreen
import red.line.tamirkar.ui.codes.SecretCodesScreen
import red.line.tamirkar.ui.customer.AddEditCustomerScreen
import red.line.tamirkar.ui.customer.CustomerScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisStartScreen
import red.line.tamirkar.ui.diagnosis.DiagnosisWizardScreen
import red.line.tamirkar.ui.home.RotaryKnobScreen
import red.line.tamirkar.ui.inventory.AddEditInventoryScreen
import red.line.tamirkar.ui.inventory.InventoryScreen
import red.line.tamirkar.ui.invoice.CreateInvoiceScreen
import red.line.tamirkar.ui.invoice.InvoiceScreen
import red.line.tamirkar.ui.models.ModelsScreen
import red.line.tamirkar.ui.more.MoreScreen
import red.line.tamirkar.ui.ota.OtaScreen
import red.line.tamirkar.ui.pinouts.PinoutsScreen
import red.line.tamirkar.ui.power.PowerDiagnosticsScreen
import red.line.tamirkar.ui.problems.DiagnosisScreen
import red.line.tamirkar.ui.problems.ProblemDetailScreen
import red.line.tamirkar.ui.problems.ProblemListScreen
import red.line.tamirkar.ui.problems.RepairGuideScreen
import red.line.tamirkar.ui.repair.AddEditRepairJobScreen
import red.line.tamirkar.ui.repair.RepairJobScreen
import red.line.tamirkar.ui.repair.RepairsScreen
import red.line.tamirkar.ui.reports.ReportScreen
import red.line.tamirkar.ui.scanner.ScannerScreen
import red.line.tamirkar.ui.schematics.SchematicsScreen
import red.line.tamirkar.ui.settings.SettingsScreen
import red.line.tamirkar.ui.tester.ComponentTesterScreen
import red.line.tamirkar.ui.tools.ToolsScreen

@Composable
fun RepairApp(
    navController: NavHostController = rememberNavController()
) {
    val items = listOf(
        BottomNavItem.Home,
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
            // ===== HOME (Rotary Knob) =====
            composable(BottomNavItem.Home.route) {
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

            // ===== BRANDS & MODELS =====
            composable(Screen.Brands.route) {
                BrandsScreen(
                    onBrandClick = { brandId ->
                        navController.navigate(Screen.Models.createRoute(brandId))
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(
                route = Screen.Models.route,
                arguments = listOf(navArgument("brandId") { type = NavType.StringType })
            ) {
                ModelsScreen(
                    onModelClick = { modelId ->
                        navController.navigate(Screen.Problems.route)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // ===== PROBLEMS =====
            composable(Screen.Problems.route) {
                ProblemListScreen(
                    onBackClick = { navController.popBackStack() },
                    onProblemClick = { problemId ->
                        navController.navigate(Screen.ProblemDetail.createRoute(problemId))
                    }
                )
            }
            composable(
                route = Screen.ProblemDetail.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) {
                ProblemDetailScreen(
                    problemId = it.arguments?.getString("problemId") ?: "",
                    onBackClick = { navController.popBackStack() },
                    onGuideClick = { problemId ->
                        navController.navigate(Screen.RepairGuide.createRoute(problemId))
                    },
                    onRelatedProblemClick = { problemId ->
                        navController.navigate(Screen.ProblemDetail.createRoute(problemId))
                    }
                )
            }
            composable(
                route = Screen.RepairGuide.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) {
                RepairGuideScreen(
                    problemId = it.arguments?.getString("problemId") ?: "",
                    onBackClick = { navController.popBackStack() }
                )
            }

            // ===== DIAGNOSIS =====
            composable(Screen.Diagnosis.route) {
                DiagnosisScreen(
                    onBackClick = { navController.popBackStack() },
                    onProblemFound = { problemId ->
                        navController.navigate(Screen.ProblemDetail.createRoute(problemId))
                    }
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
                route = Screen.DiagnosisWizard.route,
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

            // ===== REPAIRS TAB =====
            composable(BottomNavItem.Repairs.route) {
                RepairsScreen(
                    onCustomersClick = { navController.navigate(Screen.Customers.route) },
                    onRepairJobsClick = { navController.navigate(Screen.RepairJobs.route) },
                    onInventoryClick = { navController.navigate(Screen.Inventory.route) },
                    onInvoicesClick = { navController.navigate(Screen.Invoices.route) }
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
                route = Screen.AddEditCustomer.route,
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
                route = Screen.AddEditRepairJob.route,
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
            composable(Screen.Inventory.route) {
                InventoryScreen(
                    onBackClick = { navController.popBackStack() },
                    onAddItem = { navController.navigate(Screen.AddEditInventory.createRoute()) },
                    onItemClick = { itemId ->
                        navController.navigate(Screen.AddEditInventory.createRoute(itemId))
                    }
                )
            }
            composable(
                route = Screen.AddEditInventory.route,
                arguments = listOf(
                    navArgument("itemId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                AddEditInventoryScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.Invoices.route) {
                InvoiceScreen(
                    onBackClick = { navController.popBackStack() },
                    onCreateInvoice = { navController.navigate(Screen.CreateInvoice.route) },
                    onInvoiceClick = { invoiceId ->
                        // View invoice detail
                    }
                )
            }
            composable(Screen.CreateInvoice.route) {
                CreateInvoiceScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            // ===== TOOLS TAB =====
            composable(BottomNavItem.Tools.route) {
                ToolsScreen(
                    onScannerClick = { navController.navigate(Screen.Scanner.route) },
                    onReportsClick = { navController.navigate(Screen.Reports.route) }
                )
            }
            composable(Screen.Scanner.route) {
                ScannerScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.Reports.route) {
                ReportScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            // ===== MORE TAB =====
            composable(BottomNavItem.More.route) {
                MoreScreen(
                    onAboutClick = { navController.navigate(Screen.About.route) },
                    onCustomersClick = { navController.navigate(Screen.Customers.route) },
                    onRepairJobsClick = { navController.navigate(Screen.RepairJobs.route) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onBackupClick = { navController.navigate(Screen.Backup.route) },
                    onOtaClick = { navController.navigate(Screen.Ota.route) }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.Settings.route) {
                SettingsScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.Backup.route) {
                BackupScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.Ota.route) {
                OtaScreen(onBackClick = { navController.popBackStack() })
            }

            // ===== ROTARY MENU SCREENS =====
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
        }
    }
}
