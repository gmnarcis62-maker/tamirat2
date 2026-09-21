package red.line.tamirkar.ui.repair

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Customer
import red.line.tamirkar.domain.model.DeviceModel
import red.line.tamirkar.domain.model.RepairJob
import red.line.tamirkar.domain.model.RepairStatus
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

data class RepairJobFormState(
    val id: String = "",
    val customerId: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val deviceModelId: String = "",
    val deviceModelName: String = "",
    val customerDeviceId: String = "",
    val reportedProblem: String = "",
    val technicianDiagnosis: String = "",
    val status: RepairStatus = RepairStatus.RECEIVED,
    val totalCost: String = "",
    val partsCost: String = "",
    val laborCost: String = "",
    val notes: String = "",
    val isEditing: Boolean = false,
    val isValid: Boolean = false,
    val customerError: String? = null,
    val problemError: String? = null
)

@HiltViewModel
class AddEditRepairJobViewModel @Inject constructor(
    private val repository: RepairRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repairId: String? = savedStateHandle["repairId"]

    private val _formState = MutableStateFlow(RepairJobFormState())
    val formState: StateFlow<RepairJobFormState> = _formState.asStateFlow()

    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    private val _deviceModels = MutableStateFlow<List<DeviceModel>>(emptyList())
    val deviceModels: StateFlow<List<DeviceModel>> = _deviceModels.asStateFlow()

    private val _saveResult = MutableSharedFlow<Boolean>()
    val saveResult: SharedFlow<Boolean> = _saveResult.asSharedFlow()

    init {
        loadCustomers()
        loadDeviceModels()
        if (repairId != null) {
            loadRepairJob(repairId)
        }
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            _customers.value = repository.getAllCustomers()
        }
    }

    private fun loadDeviceModels() {
        viewModelScope.launch {
            _deviceModels.value = repository.getAllDeviceModels()
        }
    }

    private fun loadRepairJob(id: String) {
        viewModelScope.launch {
            val job = repository.getRepairJobById(id)
            job?.let {
                _formState.value = RepairJobFormState(
                    id = it.id,
                    customerId = it.customerId,
                    customerName = it.customerName,
                    customerPhone = it.customerPhone,
                    deviceModelId = it.deviceModelId ?: "",
                    deviceModelName = it.deviceModelName ?: "",
                    customerDeviceId = it.customerDeviceId ?: "",
                    reportedProblem = it.reportedProblem,
                    technicianDiagnosis = it.technicianDiagnosis ?: "",
                    status = it.status,
                    totalCost = it.totalCost?.toString() ?: "",
                    partsCost = it.partsCost?.toString() ?: "",
                    laborCost = it.laborCost?.toString() ?: "",
                    notes = it.notes ?: "",
                    isEditing = true,
                    isValid = true
                )
            }
        }
    }

    fun onCustomerSelected(customer: Customer) {
        _formState.value = _formState.value.copy(
            customerId = customer.id,
            customerName = customer.name,
            customerPhone = customer.phone,
            customerError = null,
            isValid = validate()
        )
    }

    fun onDeviceModelSelected(model: DeviceModel) {
        _formState.value = _formState.value.copy(
            deviceModelId = model.id,
            deviceModelName = model.name
        )
    }

    fun onReportedProblemChanged(value: String) {
        _formState.value = _formState.value.copy(
            reportedProblem = value,
            problemError = if (value.length >= 3) null else "توضیح مشکل باید حداقل ۳ کاراکتر باشد",
            isValid = validate()
        )
    }

    fun onTechnicianDiagnosisChanged(value: String) {
        _formState.value = _formState.value.copy(technicianDiagnosis = value)
    }

    fun onStatusChanged(status: RepairStatus) {
        _formState.value = _formState.value.copy(status = status)
    }

    fun onTotalCostChanged(value: String) {
        _formState.value = _formState.value.copy(totalCost = value.filter { it.isDigit() || it == '.' })
    }

    fun onPartsCostChanged(value: String) {
        _formState.value = _formState.value.copy(partsCost = value.filter { it.isDigit() || it == '.' })
    }

    fun onLaborCostChanged(value: String) {
        _formState.value = _formState.value.copy(laborCost = value.filter { it.isDigit() || it == '.' })
    }

    fun onNotesChanged(value: String) {
        _formState.value = _formState.value.copy(notes = value)
    }

    fun onCustomerDeviceIdChanged(value: String) {
        _formState.value = _formState.value.copy(customerDeviceId = value)
    }

    private fun validate(): Boolean {
        val state = _formState.value
        return state.customerId.isNotBlank() && state.reportedProblem.length >= 3
    }

    fun saveRepairJob() {
        val state = _formState.value
        if (!state.isValid) return

        viewModelScope.launch {
            try {
                val job = RepairJob(
                    id = state.id.ifBlank { java.util.UUID.randomUUID().toString() },
                    customerId = state.customerId,
                    customerName = state.customerName,
                    customerPhone = state.customerPhone,
                    deviceModelId = state.deviceModelId.ifBlank { null },
                    deviceModelName = state.deviceModelName.ifBlank { null },
                    customerDeviceId = state.customerDeviceId.ifBlank { null },
                    reportedProblem = state.reportedProblem.trim(),
                    technicianDiagnosis = state.technicianDiagnosis.trim().ifBlank { null },
                    status = state.status,
                    totalCost = state.totalCost.toDoubleOrNull(),
                    partsCost = state.partsCost.toDoubleOrNull(),
                    laborCost = state.laborCost.toDoubleOrNull(),
                    receivedAt = System.currentTimeMillis(),
                    notes = state.notes.trim().ifBlank { null }
                )
                repository.saveRepairJob(job)
                _saveResult.emit(true)
            } catch (e: Exception) {
                _saveResult.emit(false)
            }
        }
    }
}
