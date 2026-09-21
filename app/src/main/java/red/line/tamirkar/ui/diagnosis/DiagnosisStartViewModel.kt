package red.line.tamirkar.ui.diagnosis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.core.result.RepairResult
import red.line.tamirkar.data.local.entity.DeviceModelEntity
import red.line.tamirkar.data.local.entity.ProblemCategoryEntity
import red.line.tamirkar.data.local.entity.ProblemEntity
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

data class DiagnosisStartUiState(
    val categories: RepairResult<List<ProblemCategoryEntity>> = RepairResult.Loading,
    val problems: RepairResult<List<ProblemEntity>> = RepairResult.Success(emptyList()),
    val models: RepairResult<List<DeviceModelEntity>> = RepairResult.Success(emptyList()),
    val selectedCategoryId: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class DiagnosisStartViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiagnosisStartUiState())
    val uiState: StateFlow<DiagnosisStartUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            // In a real implementation, fetch from repository
            // For now, we'll use a placeholder state
            _uiState.value = _uiState.value.copy(
                categories = RepairResult.Success(emptyList())
            )
        }
    }

    fun selectCategory(categoryId: String) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId)
        loadProblems(categoryId)
    }

    private fun loadProblems(categoryId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(problems = RepairResult.Loading)
            try {
                // Fetch from repository based on category
                _uiState.value = _uiState.value.copy(
                    problems = RepairResult.Success(emptyList())
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    problems = RepairResult.Error("خطا در دریافت مشکلات")
                )
            }
        }
    }

    fun searchModels(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.length < 2) return
        viewModelScope.launch {
            val result = repository.searchModels(query)
            if (result is RepairResult.Success) {
                _uiState.value = _uiState.value.copy(
                    models = RepairResult.Success(result.data)
                )
            }
        }
    }

    fun searchProblems(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}
