package red.line.tamirkar.ui.customer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Customer
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

data class CustomerFormState(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val notes: String = "",
    val isEditing: Boolean = false,
    val isValid: Boolean = false,
    val nameError: String? = null,
    val phoneError: String? = null
)

@HiltViewModel
class AddEditCustomerViewModel @Inject constructor(
    private val repository: RepairRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val customerId: String? = savedStateHandle["customerId"]

    private val _formState = MutableStateFlow(CustomerFormState())
    val formState: StateFlow<CustomerFormState> = _formState.asStateFlow()

    private val _saveResult = MutableSharedFlow<Boolean>()
    val saveResult: SharedFlow<Boolean> = _saveResult.asSharedFlow()

    init {
        if (customerId != null) {
            loadCustomer(customerId)
        }
    }

    private fun loadCustomer(id: String) {
        viewModelScope.launch {
            val customer = repository.getCustomerById(id)
            customer?.let {
                _formState.value = CustomerFormState(
                    id = it.id,
                    name = it.name,
                    phone = it.phone,
                    email = it.email ?: "",
                    address = it.address ?: "",
                    notes = it.notes ?: "",
                    isEditing = true,
                    isValid = true
                )
            }
        }
    }

    fun onNameChanged(name: String) {
        _formState.value = _formState.value.copy(
            name = name,
            nameError = if (name.length >= 2) null else "نام باید حداقل ۲ کاراکتر باشد",
            isValid = validate(name, _formState.value.phone)
        )
    }

    fun onPhoneChanged(phone: String) {
        val digitsOnly = phone.filter { it.isDigit() }
        _formState.value = _formState.value.copy(
            phone = digitsOnly,
            phoneError = if (digitsOnly.length >= 10) null else "شماره تماس باید حداقل ۱۰ رقم باشد",
            isValid = validate(_formState.value.name, digitsOnly)
        )
    }

    fun onEmailChanged(email: String) {
        _formState.value = _formState.value.copy(email = email)
    }

    fun onAddressChanged(address: String) {
        _formState.value = _formState.value.copy(address = address)
    }

    fun onNotesChanged(notes: String) {
        _formState.value = _formState.value.copy(notes = notes)
    }

    private fun validate(name: String, phone: String): Boolean {
        return name.length >= 2 && phone.length >= 10
    }

    fun saveCustomer() {
        val state = _formState.value
        if (!state.isValid) return

        viewModelScope.launch {
            try {
                val customer = Customer(
                    id = state.id.ifBlank { java.util.UUID.randomUUID().toString() },
                    name = state.name.trim(),
                    phone = state.phone.trim(),
                    email = state.email.trim().ifBlank { null },
                    address = state.address.trim().ifBlank { null },
                    notes = state.notes.trim().ifBlank { null }
                )
                repository.saveCustomer(customer)
                _saveResult.emit(true)
            } catch (e: Exception) {
                _saveResult.emit(false)
            }
        }
    }
}
