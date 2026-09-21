package red.line.tamirkar.ui.problem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.data.local.seed.ProblemSeedData
import red.line.tamirkar.domain.model.*
import javax.inject.Inject

@HiltViewModel
class ProblemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val problemId: String = savedStateHandle["problemId"] ?: ""

    private val _problem = MutableStateFlow<ProblemDetail?>(null)
    val problem: StateFlow<ProblemDetail?> = _problem.asStateFlow()

    private val _selectedCause = MutableStateFlow<ProblemCause?>(null)
    val selectedCause: StateFlow<ProblemCause?> = _selectedCause.asStateFlow()

    private val _selectedSolution = MutableStateFlow<ProblemSolution?>(null)
    val selectedSolution: StateFlow<ProblemSolution?> = _selectedSolution.asStateFlow()

    private val _expandedSections = MutableStateFlow<Set<String>>(emptySet())
    val expandedSections: StateFlow<Set<String>> = _expandedSections.asStateFlow()

    init {
        loadProblem()
    }

    private fun loadProblem() {
        val allProblems = ProblemSeedData.getAllProblems()
        _problem.value = allProblems.find { it.id == problemId }
    }

    fun toggleSection(section: String) {
        _expandedSections.value = _expandedSections.value.toMutableSet().apply {
            if (contains(section)) remove(section) else add(section)
        }
    }

    fun selectCause(cause: ProblemCause?) {
        _selectedCause.value = cause
    }

    fun selectSolution(solution: ProblemSolution?) {
        _selectedSolution.value = solution
    }

    fun getSeverityColor(): String {
        return _problem.value?.severity?.color ?: "#757575"
    }

    fun getDifficultyStars(): Int {
        return _problem.value?.difficulty?.stars ?: 1
    }
}
