package red.line.tamirkar.ui.repair

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import red.line.tamirkar.domain.model.Customer
import red.line.tamirkar.domain.model.DeviceModel
import red.line.tamirkar.domain.model.RepairStatus

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditRepairJobScreen(
    onBackClick: () -> Unit,
    viewModel: AddEditRepairJobViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val deviceModels by viewModel.deviceModels.collectAsState()
    val scrollState = rememberScrollState()

    var showCustomerSheet by remember { mutableStateOf(false) }
    var showModelSheet by remember { mutableStateOf(false) }
    var showStatusMenu by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.saveResult.collectLatest { success ->
            if (success) onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (formState.isEditing) "ویرایش تعمیر" else "تعمیر جدید") },
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
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (formState.isEditing) Icons.Default.Edit else Icons.Default.Handyman,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Customer Selection
            Text(
                text = "مشتری *",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (formState.customerName.isNotBlank()) {
                ElevatedCard(
                    onClick = { showCustomerSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = formState.customerName.take(1),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = formState.customerName,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = formState.customerPhone,
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
                    onClick = { showCustomerSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PersonSearch, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("انتخاب مشتری")
                }
            }
            if (formState.customerError != null) {
                Text(
                    text = formState.customerError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Device Model Selection
            Text(
                text = "مدل دستگاه",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (formState.deviceModelName.isNotBlank()) {
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Smartphone, contentDescription = null)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = formState.deviceModelName,
                                style = MaterialTheme.typography.bodyLarge
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
                    Icon(Icons.Default.PhoneAndroid, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("انتخاب مدل دستگاه")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Customer Device ID
            OutlinedTextField(
                value = formState.customerDeviceId,
                onValueChange = { viewModel.onCustomerDeviceIdChanged(it) },
                label = { Text("شناسه دستگاه مشتری (IMEI/سریال)") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Fingerprint, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Reported Problem
            OutlinedTextField(
                value = formState.reportedProblem,
                onValueChange = { viewModel.onReportedProblemChanged(it) },
                label = { Text("مشکل گزارش‌شده *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.ReportProblem, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                isError = formState.problemError != null,
                supportingText = formState.problemError?.let { { Text(it) } },
                minLines = 2,
                maxLines = 3,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Technician Diagnosis
            OutlinedTextField(
                value = formState.technicianDiagnosis,
                onValueChange = { viewModel.onTechnicianDiagnosisChanged(it) },
                label = { Text("تشخیص تکنسین") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.MedicalServices, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                minLines = 2,
                maxLines = 3,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Status Selection
            Text(
                text = "وضعیت",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RepairStatus.entries.forEach { status ->
                    val isSelected = formState.status == status
                    val statusColor = androidx.compose.ui.graphics.Color(
                        android.graphics.Color.parseColor(status.colorHex)
                    )
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.onStatusChanged(status) },
                        label = { Text(status.label) },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = statusColor.copy(alpha = 0.15f),
                            selectedLabelColor = statusColor
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Costs
            Text(
                text = "هزینه‌ها (تومان)",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = formState.partsCost,
                    onValueChange = { viewModel.onPartsCostChanged(it) },
                    label = { Text("قطعات") },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = formState.laborCost,
                    onValueChange = { viewModel.onLaborCostChanged(it) },
                    label = { Text("دستمزد") },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.Engineering, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = formState.totalCost,
                onValueChange = { viewModel.onTotalCostChanged(it) },
                label = { Text("جمع کل") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Calculate, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Notes
            OutlinedTextField(
                value = formState.notes,
                onValueChange = { viewModel.onNotesChanged(it) },
                label = { Text("یادداشت") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                minLines = 3,
                maxLines = 5,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Info card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "فیلدهای ستاره‌دار الزامی هستند. شناسه دستگاه (IMEI) برای جلوگیری از اشتباه در تحویل استفاده می‌شود.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save button
            Button(
                onClick = { viewModel.saveRepairJob() },
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.isValid
            ) {
                Icon(
                    if (formState.isEditing) Icons.Default.Save else Icons.Default.Add,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (formState.isEditing) "ذخیره تغییرات" else "ثبت تعمیر")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Customer Selection Bottom Sheet
    if (showCustomerSheet) {
        CustomerSelectionBottomSheet(
            customers = customers,
            onDismiss = { showCustomerSheet = false },
            onCustomerSelected = {
                viewModel.onCustomerSelected(it)
                showCustomerSheet = false
            }
        )
    }

    // Device Model Selection Bottom Sheet
    if (showModelSheet) {
        DeviceModelSelectionBottomSheet(
            models = deviceModels,
            onDismiss = { showModelSheet = false },
            onModelSelected = {
                viewModel.onDeviceModelSelected(it)
                showModelSheet = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerSelectionBottomSheet(
    customers: List<Customer>,
    onDismiss: () -> Unit,
    onCustomerSelected: (Customer) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var searchQuery by remember { mutableStateOf("") }

    val filtered = if (searchQuery.length < 2) customers else customers.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.phone.contains(searchQuery)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "انتخاب مشتری",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("جستجو مشتری...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "مشتری یافت نشد",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                filtered.forEach { customer ->
                    ElevatedCard(
                        onClick = { onCustomerSelected(customer) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = customer.name.take(1),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = customer.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = customer.phone,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceModelSelectionBottomSheet(
    models: List<DeviceModel>,
    onDismiss: () -> Unit,
    onModelSelected: (DeviceModel) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var searchQuery by remember { mutableStateOf("") }

    val filtered = if (searchQuery.length < 2) models else models.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.modelNumber.contains(searchQuery, ignoreCase = true)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "انتخاب مدل دستگاه",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("جستجو مدل...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "مدلی یافت نشد",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                filtered.forEach { model ->
                    ElevatedCard(
                        onClick = { onModelSelected(model) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Smartphone,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = model.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = model.modelNumber,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
