package red.line.tamirkar.ui.invoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

data class InvoiceItemForm(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String = "",
    val quantity: String = "1",
    val unitPrice: String = "",
    val nameError: String? = null
)

data class CreateInvoiceFormState(
    val customerId: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val deviceModel: String = "",
    val invoiceNumber: String = "",
    val items: List<InvoiceItemForm> = listOf(InvoiceItemForm()),
    val laborCost: String = "",
    val discount: String = "",
    val notes: String = "",
    val isPaid: Boolean = false,
    val paymentMethod: PaymentMethod? = null,
    val isValid: Boolean = false
)

@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(CreateInvoiceFormState())
    val formState: StateFlow<CreateInvoiceFormState> = _formState.asStateFlow()

    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    private val _saveResult = MutableSharedFlow<Boolean>()
    val saveResult: SharedFlow<Boolean> = _saveResult.asSharedFlow()

    init {
        loadCustomers()
        generateInvoiceNumber()
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            _customers.value = repository.getAllCustomers()
        }
    }

    private fun generateInvoiceNumber() {
        val timestamp = System.currentTimeMillis()
        val random = (1000..9999).random()
        val invoiceNum = "INV-${timestamp.toString().takeLast(6)}-$random"
        _formState.value = _formState.value.copy(invoiceNumber = invoiceNum)
    }

    fun onCustomerSelected(customer: Customer) {
        _formState.value = _formState.value.copy(
            customerId = customer.id,
            customerName = customer.name,
            customerPhone = customer.phone,
            isValid = validate()
        )
    }

    fun onDeviceModelChanged(value: String) {
        _formState.value = _formState.value.copy(deviceModel = value)
    }

    fun onLaborCostChanged(value: String) {
        val filtered = value.filter { it.isDigit() || it == '.' }
        _formState.value = _formState.value.copy(laborCost = filtered, isValid = validate())
    }

    fun onDiscountChanged(value: String) {
        val filtered = value.filter { it.isDigit() || it == '.' }
        _formState.value = _formState.value.copy(discount = filtered)
    }

    fun onNotesChanged(value: String) {
        _formState.value = _formState.value.copy(notes = value)
    }

    fun onIsPaidChanged(value: Boolean) {
        _formState.value = _formState.value.copy(isPaid = value)
    }

    fun onPaymentMethodChanged(value: PaymentMethod?) {
        _formState.value = _formState.value.copy(paymentMethod = value)
    }

    fun addItem() {
        val current = _formState.value
        _formState.value = current.copy(items = current.items + InvoiceItemForm())
    }

    fun removeItem(index: Int) {
        val current = _formState.value
        if (current.items.size > 1) {
            val newItems = current.items.toMutableList()
            newItems.removeAt(index)
            _formState.value = current.copy(items = newItems, isValid = validate())
        }
    }

    fun updateItemName(index: Int, value: String) {
        val current = _formState.value
        val newItems = current.items.toMutableList()
        newItems[index] = newItems[index].copy(
            name = value,
            nameError = if (value.length >= 2) null else "نام کالا باید حداقل ۲ کارکتر باشد"
        )
        _formState.value = current.copy(items = newItems, isValid = validate())
    }

    fun updateItemQuantity(index: Int, value: String) {
        val current = _formState.value
        val newItems = current.items.toMutableList()
        newItems[index] = newItems[index].copy(quantity = value.filter { it.isDigit() }.ifEmpty { "1" })
        _formState.value = current.copy(items = newItems, isValid = validate())
    }

    fun updateItemUnitPrice(index: Int, value: String) {
        val current = _formState.value
        val newItems = current.items.toMutableList()
        newItems[index] = newItems[index].copy(unitPrice = value.filter { it.isDigit() || it == '.' })
        _formState.value = current.copy(items = newItems, isValid = validate())
    }

    fun calculateTotals(): Triple<Double, Double, Double> {
        val state = _formState.value
        val itemsTotal = state.items.sumOf {
            (it.quantity.toIntOrNull() ?: 0) * (it.unitPrice.toDoubleOrNull() ?: 0.0)
        }
        val labor = state.laborCost.toDoubleOrNull() ?: 0.0
        val discount = state.discount.toDoubleOrNull() ?: 0.0
        val total = itemsTotal + labor
        val final = (total - discount).coerceAtLeast(0.0)
        return Triple(itemsTotal, total, final)
    }

    private fun validate(): Boolean {
        val state = _formState.value
        val hasValidItems = state.items.isNotEmpty() && state.items.all {
            it.name.length >= 2 && (it.unitPrice.toDoubleOrNull() ?: 0.0) > 0
        }
        return state.customerId.isNotBlank() && hasValidItems
    }

    fun saveInvoice() {
        val state = _formState.value
        if (!state.isValid) return

        viewModelScope.launch {
            try {
                val (itemsTotal, total, final) = calculateTotals()
                val invoiceItems = state.items.map {
                    InvoiceItem(
                        id = it.id,
                        name = it.name,
                        quantity = it.quantity.toIntOrNull() ?: 1,
                        unitPrice = it.unitPrice.toDoubleOrNull() ?: 0.0
                    )
                }
                val invoice = Invoice(
                    id = java.util.UUID.randomUUID().toString(),
                    customerName = state.customerName,
                    customerPhone = state.customerPhone,
                    deviceModel = state.deviceModel.ifBlank { null },
                    invoiceNumber = state.invoiceNumber,
                    items = invoiceItems,
                    laborCost = state.laborCost.toDoubleOrNull() ?: 0.0,
                    discount = state.discount.toDoubleOrNull() ?: 0.0,
                    totalAmount = total,
                    finalAmount = final,
                    isPaid = state.isPaid,
                    paymentMethod = state.paymentMethod,
                    notes = state.notes.ifBlank { null }
                )
                repository.saveInvoice(invoice)
                _saveResult.emit(true)
            } catch (e: Exception) {
                _saveResult.emit(false)
            }
        }
    }
}
