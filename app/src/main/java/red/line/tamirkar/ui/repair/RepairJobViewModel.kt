package red.line.tamirkar.ui.repair

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.RepairJob
import red.line.tamirkar.domain.model.RepairStatus
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

sealed class RepairJobUiState {
    object Loading : RepairJobUiState()
    data class Success(
        val repairs: List<RepairJob>,
        val filterStatus: RepairStatus? = null,
        val searchQuery: String = ""
    ) : RepairJobUiState()
    data class Error(val message: String) : RepairJobUiState()
}

@HiltViewModel
class RepairJobViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RepairJobUiState>(RepairJobUiState.Loading)
    val uiState: StateFlow<RepairJobUiState> = _uiState.asStateFlow()

    private var allRepairs: List<RepairJob> = emptyList()

    init {
        loadRepairs()
    }

    private fun loadRepairs() {
        viewModelScope.launch {
            _uiState.value = RepairJobUiState.Loading
            try {
                allRepairs = repository.getAllRepairJobs()
                _uiState.value = RepairJobUiState.Success(allRepairs)
            } catch (e: Exception) {
                _uiState.value = RepairJobUiState.Error("خطا در دریافت لیست تعمیرات: ${e.localizedMessage}")
            }
        }
    }

    fun filterByStatus(status: RepairStatus?) {
        val current = _uiState.value as? RepairJobUiState.Success ?: return
        val filtered = if (status == null) {
            allRepairs
        } else {
            allRepairs.filter { it.status == status }
        }
        _uiState.value = current.copy(
            repairs = filtered,
            filterStatus = status
        )
    }

    fun search(query: String) {
        val current = _uiState.value as? RepairJobUiState.Success ?: return
        val filtered = if (query.isBlank()) {
            allRepairs
        } else {
            allRepairs.filter {
                it.customerName.contains(query, ignoreCase = true) ||
                it.customerPhone.contains(query) ||
                it.deviceModelName?.contains(query, ignoreCase = true) == true ||
                it.reportedProblem.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = current.copy(
            repairs = filtered,
            searchQuery = query
        )
    }

    fun deleteRepair(repairId: String) {
        viewModelScope.launch {
            try {
                repository.deleteRepairJob(repairId)
                loadRepairs()
            } catch (e: Exception) {
                _uiState.value = RepairJobUiState.Error("خطا در حذف تعمیر")
            }
        }
    }

    fun refresh() {
        loadRepairs()
    }
}
