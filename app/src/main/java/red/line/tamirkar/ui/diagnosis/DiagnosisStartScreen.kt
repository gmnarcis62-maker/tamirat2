package red.line.tamirkar.ui.diagnosis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import red.line.tamirkar.core.result.RepairResult
import red.line.tamirkar.data.local.entity.DeviceModelEntity
import red.line.tamirkar.data.local.entity.ProblemEntity
import red.line.tamirkar.ui.brands.BrandItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisStartScreen(
    onBackClick: () -> Unit,
    onStartDiagnosis: (problemId: String, modelId: String?) -> Unit,
    viewModel: DiagnosisStartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedModel by remember { mutableStateOf<DeviceModelEntity?>(null) }
    var selectedProblem by remember { mutableStateOf<ProblemEntity?>(null) }
    var showModelSheet by remember { mutableStateOf(false) }
    var showProblemSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("عیب‌یابی جدید") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "عیب‌یابی هوشمند دستگاه",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "مدل دستگاه و مشکل را انتخاب کنید تا راهنمای مرحله‌ای دریافت کنید.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selected Model Card
            Text(
                text = "دستگاه",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (selectedModel != null) {
                ElevatedCard(
                    onClick = { showModelSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = selectedModel!!.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = selectedModel!!.modelNumber,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Icon(Icons.Default.Edit, contentDescription = "تغییر")
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { showModelSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Smartphone, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("انتخاب مدل دستگاه")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selected Problem Card
            Text(
                text = "مشکل",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (selectedProblem != null) {
                ElevatedCard(
                    onClick = { showProblemSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = selectedProblem!!.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (!selectedProblem!!.titleEn.isNullOrBlank()) {
                                Text(
                                    text = selectedProblem!!.titleEn,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                        Icon(Icons.Default.Edit, contentDescription = "تغییر")
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { showProblemSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ReportProblem, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("انتخاب مشکل")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Start Button
            Button(
                onClick = {
                    selectedProblem?.let { problem ->
                        onStartDiagnosis(problem.id, selectedModel?.id)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedProblem != null
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("شروع عیب‌یابی")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick common problems
            Text(
                text = "مشکلات رایج",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            val commonProblems = listOf(
                "گوشی روشن نمی‌شود" to "problem_no_power",
                "شارژ نمی‌شود" to "problem_no_charge",
                "بوت‌لوپ" to "problem_bootloop",
                "تاچ کار نمی‌کند" to "problem_touch_issue",
                "آنتن ندارد" to "problem_no_signal"
            )

            commonProblems.forEach { (title, problemId) ->
                ElevatedCard(
                    onClick = {
                        // In real app, fetch from DB
                        selectedProblem = ProblemEntity(
                            id = problemId,
                            categoryId = "power",
                            title = title,
                            titleEn = "",
                            slug = ""
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Box(modifier = Modifier.padding(14.dp)) {
                        Text(title, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Model Selection Bottom Sheet
    if (showModelSheet) {
        ModelSelectionBottomSheet(
            onDismiss = { showModelSheet = false },
            onModelSelected = {
                selectedModel = it
                showModelSheet = false
            }
        )
    }

    // Problem Selection Bottom Sheet
    if (showProblemSheet) {
        ProblemSelectionBottomSheet(
            onDismiss = { showProblemSheet = false },
            onProblemSelected = {
                selectedProblem = it
                showProblemSheet = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelSelectionBottomSheet(
    onDismiss: () -> Unit,
    onModelSelected: (DeviceModelEntity) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "انتخاب مدل دستگاه",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("جستجوی مدل...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // In real app, show list from ViewModel
            Text("لیست مدل‌ها اینجا نمایش داده می‌شود", color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemSelectionBottomSheet(
    onDismiss: () -> Unit,
    onProblemSelected: (ProblemEntity) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "انتخاب مشکل",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Categories
            val categories = listOf(
                "تغذیه و روشن شدن" to Icons.Default.Power,
                "شارژ" to Icons.Default.BatteryChargingFull,
                "نمایشگر و تاچ" to Icons.Default.ScreenLockPortrait,
                "شبکه" to Icons.Default.SignalCellularAlt,
                "صدا" to Icons.Default.VolumeUp,
                "دوربین" to Icons.Default.CameraAlt,
                "نرم‌افزار" to Icons.Default.SystemUpdate
            )
            categories.forEach { (title, icon) ->
                ElevatedCard(
                    onClick = { /* Filter problems */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(title, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
