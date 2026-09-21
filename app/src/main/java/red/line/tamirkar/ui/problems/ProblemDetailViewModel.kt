package red.line.tamirkar.ui.problems

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Problem
import red.line.tamirkar.domain.model.RepairGuide
import red.line.tamirkar.domain.repository.ProblemRepository
import javax.inject.Inject

@HiltViewModel
class ProblemDetailViewModel @Inject constructor(
    private val repository: ProblemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val problemId: String = savedStateHandle["problemId"] ?: ""

    private val _problem = MutableStateFlow<Problem?>(null)
    val problem: StateFlow<Problem?> = _problem.asStateFlow()

    private val _repairGuide = MutableStateFlow<RepairGuide?>(null)
    val repairGuide: StateFlow<RepairGuide?> = _repairGuide.asStateFlow()

    private val _relatedProblems = MutableStateFlow<List<Problem>>(emptyList())
    val relatedProblems: StateFlow<List<Problem>> = _relatedProblems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadProblemDetails()
    }

    private fun loadProblemDetails() {
        if (problemId.isBlank()) return
        viewModelScope.launch {
            _isLoading.value = true
            _problem.value = repository.getProblemById(problemId)
            _repairGuide.value = repository.getRepairGuide(problemId)
            _relatedProblems.value = repository.getRelatedProblems(problemId)
            _isLoading.value = false
        }
    }

    fun refresh() {
        loadProblemDetails()
    }
}
