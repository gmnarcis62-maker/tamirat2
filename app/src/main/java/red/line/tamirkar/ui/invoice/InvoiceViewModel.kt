package red.line.tamirkar.ui.invoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Invoice
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

sealed class InvoiceUiState {
    object Loading : InvoiceUiState()
    data class Success(
        val invoices: List<Invoice>,
        val searchQuery: String = "",
        val totalRevenue: Double = 0.0,
        val paidAmount: Double = 0.0,
        val unpaidAmount: Double = 0.0
    ) : InvoiceUiState()
    data class Error(val message: String) : InvoiceUiState()
}

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<InvoiceUiState>(InvoiceUiState.Loading)
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    private var allInvoices: List<Invoice> = emptyList()

    init {
        loadInvoices()
    }

    private fun loadInvoices() {
        viewModelScope.launch {
            _uiState.value = InvoiceUiState.Loading
            try {
                allInvoices = repository.getAllInvoices()
                val totalRevenue = allInvoices.sumOf { it.finalAmount }
                val paidAmount = allInvoices.filter { it.isPaid }.sumOf { it.finalAmount }
                _uiState.value = InvoiceUiState.Success(
                    invoices = allInvoices,
                    totalRevenue = totalRevenue,
                    paidAmount = paidAmount,
                    unpaidAmount = totalRevenue - paidAmount
                )
            } catch (e: Exception) {
                _uiState.value = InvoiceUiState.Error("خطا در دریافت لیست فاکتورها: ${e.localizedMessage}")
            }
        }
    }

    fun search(query: String) {
        val current = _uiState.value as? InvoiceUiState.Success ?: return
        val filtered = if (query.isBlank()) {
            allInvoices
        } else {
            allInvoices.filter {
                it.customerName.contains(query, ignoreCase = true) ||
                it.invoiceNumber.contains(query) ||
                it.deviceModel?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = current.copy(invoices = filtered, searchQuery = query)
    }

    fun deleteInvoice(invoiceId: String) {
        viewModelScope.launch {
            try {
                repository.deleteInvoice(invoiceId)
                loadInvoices()
            } catch (e: Exception) {
                _uiState.value = InvoiceUiState.Error("خطا در حذف فاکتور")
            }
        }
    }

    fun refresh() {
        loadInvoices()
    }
}
