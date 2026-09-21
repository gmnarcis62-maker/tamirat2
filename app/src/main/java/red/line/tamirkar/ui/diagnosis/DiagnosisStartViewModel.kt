package red.line.tamirkar.ui.diagnosis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Problem
import red.line.tamirkar.domain.repository.ProblemRepository
import javax.inject.Inject

@HiltViewModel
class DiagnosisStartViewModel @Inject constructor(
    private val problemRepository: ProblemRepository
) : ViewModel() {

    private val _problems = MutableStateFlow<List<Problem>>(emptyList())
    val problems: StateFlow<List<Problem>> = _problems.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadProblems()
    }

    private fun loadProblems() {
        viewModelScope.launch {
            _isLoading.value = true
            _problems.value = problemRepository.getAllProblems()
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _problems.value = if (query.isBlank()) {
                problemRepository.getAllProblems()
            } else {
                problemRepository.searchProblems(query)
            }
        }
    }
}