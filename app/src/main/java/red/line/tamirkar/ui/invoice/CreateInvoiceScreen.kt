package red.line.tamirkar.ui.invoice

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
import red.line.tamirkar.domain.model.PaymentMethod

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateInvoiceScreen(
    onBackClick: () -> Unit,
    viewModel: CreateInvoiceViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val scrollState = rememberScrollState()

    var showCustomerSheet by remember { mutableStateOf(false) }

    val (itemsTotal, total, final) = viewModel.calculateTotals()

    LaunchedEffect(Unit) {
        viewModel.saveResult.collectLatest { success ->
            if (success) onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("فاکتور جدید") },
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

            // Invoice Number
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ConfirmationNumber,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "شماره فاکتور",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                        )
                        Text(
                            text = formState.invoiceNumber,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = formState.customerName.take(1),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
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

            Spacer(modifier = Modifier.height(20.dp))

            // Device Model
            OutlinedTextField(
                value = formState.deviceModel,
                onValueChange = { viewModel.onDeviceModelChanged(it) },
                label = { Text("مدل دستگاه") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Smartphone, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Invoice Items
            Text(
                text = "اقلام فاکتور",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            formState.items.forEachIndexed { index, item ->
                InvoiceItemCard(
                    index = index,
                    item = item,
                    onNameChange = { viewModel.updateItemName(index, it) },
                    onQuantityChange = { viewModel.updateItemQuantity(index, it) },
                    onUnitPriceChange = { viewModel.updateItemUnitPrice(index, it) },
                    onRemove = { viewModel.removeItem(index) },
                    canRemove = formState.items.size > 1
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Add Item Button
            OutlinedButton(
                onClick = { viewModel.addItem() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("افزودن قلم کالا")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Labor Cost and Discount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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
                OutlinedTextField(
                    value = formState.discount,
                    onValueChange = { viewModel.onDiscountChanged(it) },
                    label = { Text("تخفیف") },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.LocalOffer, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Payment Status
            Text(
                text = "وضعیت پرداخت",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = formState.isPaid,
                    onCheckedChange = { viewModel.onIsPaidChanged(it) }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = if (formState.isPaid) "پرداخت شده" else "پرداخت نشده",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (formState.isPaid) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "روش پرداخت",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PaymentMethod.entries.forEach { method ->
                        FilterChip(
                            selected = formState.paymentMethod == method,
                            onClick = { viewModel.onPaymentMethodChanged(method) },
                            label = { Text(method.label) },
                            leadingIcon = if (formState.paymentMethod == method) {
                                {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            } else null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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

            // Totals Card
            InvoiceTotalsCard(
                itemsTotal = itemsTotal,
                laborCost = formState.laborCost.toDoubleOrNull() ?: 0.0,
                discount = formState.discount.toDoubleOrNull() ?: 0.0,
                total = total,
                final = final
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = { viewModel.saveInvoice() },
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.isValid
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ثبت فاکتور")
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
}

@Composable
fun InvoiceItemCard(
    index: Int,
    item: InvoiceItemForm,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onUnitPriceChange: (String) -> Unit,
    onRemove: () -> Unit,
    canRemove: Boolean
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "قلم ${index + 1}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                if (canRemove) {
                    IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.RemoveCircle,
                            contentDescription = "حذف",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = item.name,
                onValueChange = onNameChange,
                label = { Text("نام کالا *") },
                modifier = Modifier.fillMaxWidth(),
                isError = item.nameError != null,
                supportingText = item.nameError?.let { { Text(it) } },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = item.quantity,
                    onValueChange = onQuantityChange,
                    label = { Text("تعداد") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = item.unitPrice,
                    onValueChange = onUnitPriceChange,
                    label = { Text("قیمت واحد") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }

            val itemTotal = (item.quantity.toIntOrNull() ?: 0) * (item.unitPrice.toDoubleOrNull() ?: 0.0)
            if (itemTotal > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "جمع: ${itemTotal.toInt()} تومان",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun InvoiceTotalsCard(
    itemsTotal: Double,
    laborCost: Double,
    discount: Double,
    total: Double,
    final: Double
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "خلاصه فاکتور",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))

            TotalRow("جمع اقلام:", itemsTotal)
            if (laborCost > 0) {
                TotalRow("دستمزد:", laborCost)
            }
            if (discount > 0) {
                TotalRow("تخفیف:", -discount, isNegative = true)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "مبلغ قابل پرداخت:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "${final.toInt()} تومان",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun TotalRow(label: String, amount: Double, isNegative: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = "${if (isNegative) "-" else ""}${amount.toInt()} تومان",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isNegative) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
