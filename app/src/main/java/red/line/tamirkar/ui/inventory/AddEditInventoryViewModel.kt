package red.line.tamirkar.ui.inventory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.InventoryCategory
import red.line.tamirkar.domain.model.InventoryItem
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

data class InventoryFormState(
    val id: String = "",
    val partNumber: String = "",
    val name: String = "",
    val category: InventoryCategory = InventoryCategory.OTHER,
    val quantity: String = "0",
    val minQuantity: String = "0",
    val purchasePrice: String = "",
    val salePrice: String = "",
    val supplier: String = "",
    val location: String = "",
    val notes: String = "",
    val isEditing: Boolean = false,
    val isValid: Boolean = false,
    val nameError: String? = null
)

@HiltViewModel
class AddEditInventoryViewModel @Inject constructor(
    private val repository: RepairRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: String? = savedStateHandle["itemId"]

    private val _formState = MutableStateFlow(InventoryFormState())
    val formState: StateFlow<InventoryFormState> = _formState.asStateFlow()

    private val _saveResult = MutableSharedFlow<Boolean>()
    val saveResult: SharedFlow<Boolean> = _saveResult.asSharedFlow()

    init {
        if (itemId != null) {
            loadItem(itemId)
        }
    }

    private fun loadItem(id: String) {
        viewModelScope.launch {
            val item = repository.getInventoryItemById(id)
            item?.let {
                _formState.value = InventoryFormState(
                    id = it.id,
                    partNumber = it.partNumber ?: "",
                    name = it.name,
                    category = InventoryCategory.valueOf(it.category ?: "OTHER"),
                    quantity = it.quantity.toString(),
                    minQuantity = it.minQuantity.toString(),
                    purchasePrice = it.purchasePrice?.toString() ?: "",
                    salePrice = it.salePrice?.toString() ?: "",
                    supplier = it.supplier ?: "",
                    location = it.location ?: "",
                    notes = it.notes ?: "",
                    isEditing = true,
                    isValid = true
                )
            }
        }
    }

    fun onNameChanged(value: String) {
        _formState.value = _formState.value.copy(
            name = value,
            nameError = if (value.length >= 2) null else "نام قطعه باید حداقل ۲ کاراکتر باشد",
            isValid = validate(value)
        )
    }

    fun onPartNumberChanged(value: String) {
        _formState.value = _formState.value.copy(partNumber = value)
    }

    fun onCategoryChanged(value: InventoryCategory) {
        _formState.value = _formState.value.copy(category = value)
    }

    fun onQuantityChanged(value: String) {
        val digitsOnly = value.filter { it.isDigit() }
        _formState.value = _formState.value.copy(quantity = digitsOnly.ifEmpty { "0" })
    }

    fun onMinQuantityChanged(value: String) {
        val digitsOnly = value.filter { it.isDigit() }
        _formState.value = _formState.value.copy(minQuantity = digitsOnly.ifEmpty { "0" })
    }

    fun onPurchasePriceChanged(value: String) {
        val filtered = value.filter { it.isDigit() || it == '.' }
        _formState.value = _formState.value.copy(purchasePrice = filtered)
    }

    fun onSalePriceChanged(value: String) {
        val filtered = value.filter { it.isDigit() || it == '.' }
        _formState.value = _formState.value.copy(salePrice = filtered)
    }

    fun onSupplierChanged(value: String) {
        _formState.value = _formState.value.copy(supplier = value)
    }

    fun onLocationChanged(value: String) {
        _formState.value = _formState.value.copy(location = value)
    }

    fun onNotesChanged(value: String) {
        _formState.value = _formState.value.copy(notes = value)
    }

    private fun validate(name: String): Boolean {
        return name.length >= 2
    }

    fun saveItem() {
        val state = _formState.value
        if (!state.isValid) return

        viewModelScope.launch {
            try {
                val item = InventoryItem(
                    id = state.id.ifBlank { java.util.UUID.randomUUID().toString() },
                    partNumber = state.partNumber.trim().ifBlank { null },
                    name = state.name.trim(),
                    category = state.category.name,
                    quantity = state.quantity.toIntOrNull() ?: 0,
                    minQuantity = state.minQuantity.toIntOrNull() ?: 0,
                    purchasePrice = state.purchasePrice.toDoubleOrNull(),
                    salePrice = state.salePrice.toDoubleOrNull(),
                    supplier = state.supplier.trim().ifBlank { null },
                    location = state.location.trim().ifBlank { null },
                    notes = state.notes.trim().ifBlank { null }
                )
                repository.saveInventoryItem(item)
                _saveResult.emit(true)
            } catch (e: Exception) {
                _saveResult.emit(false)
            }
        }
    }
}
