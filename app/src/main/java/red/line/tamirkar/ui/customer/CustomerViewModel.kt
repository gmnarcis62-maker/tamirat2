package red.line.tamirkar.ui.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Customer
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

sealed class CustomerUiState {
    object Loading : CustomerUiState()
    data class Success(val customers: List<Customer>, val searchQuery: String = "") : CustomerUiState()
    data class Error(val message: String) : CustomerUiState()
}

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CustomerUiState>(CustomerUiState.Loading)
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()

    private var allCustomers: List<Customer> = emptyList()

    init {
        loadCustomers()
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            _uiState.value = CustomerUiState.Loading
            try {
                allCustomers = repository.getAllCustomers()
                _uiState.value = CustomerUiState.Success(allCustomers)
            } catch (e: Exception) {
                _uiState.value = CustomerUiState.Error("خطا در دریافت لیست مشتریان: ${e.localizedMessage}")
            }
        }
    }

    fun search(query: String) {
        val current = _uiState.value as? CustomerUiState.Success ?: return
        val filtered = if (query.isBlank()) {
            allCustomers
        } else {
            allCustomers.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.phone.contains(query) ||
                it.address?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = current.copy(customers = filtered, searchQuery = query)
    }

    fun deleteCustomer(customerId: String) {
        viewModelScope.launch {
            try {
                repository.deleteCustomer(customerId)
                loadCustomers()
            } catch (e: Exception) {
                _uiState.value = CustomerUiState.Error("خطا در حذف مشتری")
            }
        }
    }

    fun refresh() {
        loadCustomers()
    }
}
