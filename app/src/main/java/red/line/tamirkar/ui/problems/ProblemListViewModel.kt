package red.line.tamirkar.ui.problems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.Problem
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.repository.ProblemRepository
import javax.inject.Inject

@HiltViewModel
class ProblemListViewModel @Inject constructor(
    private val repository: ProblemRepository
) : ViewModel() {

    private val _problems = MutableStateFlow<List<Problem>>(emptyList())
    val problems: StateFlow<List<Problem>> = _problems.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<ProblemCategory?>(null)
    val selectedCategory: StateFlow<ProblemCategory?> = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadProblems()
    }

    fun loadProblems() {
        viewModelScope.launch {
            _isLoading.value = true
            _problems.value = repository.getAllProblems()
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _problems.value = if (query.isBlank()) {
                repository.getAllProblems()
            } else {
                repository.searchProblems(query)
            }
        }
    }

    fun onCategorySelected(category: ProblemCategory?) {
        _selectedCategory.value = category
        viewModelScope.launch {
            _problems.value = if (category == null) {
                repository.getAllProblems()
            } else {
                repository.getProblemsByCategory(category)
            }
        }
    }

    fun getCommonProblems(): List<Problem> {
        return _problems.value.filter { it.isCommon }
    }

    fun getProblemsBySeverity(severity: ProblemSeverity): List<Problem> {
        return _problems.value.filter { it.severity == severity }
    }
}
