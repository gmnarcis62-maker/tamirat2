package red.line.tamirkar.ui.problems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.DiagnosisNode
import red.line.tamirkar.domain.model.Problem
import red.line.tamirkar.domain.repository.ProblemRepository
import javax.inject.Inject

sealed class DiagnosisState {
    object Loading : DiagnosisState()
    data class Question(val node: DiagnosisNode, val path: List<DiagnosisNode>) : DiagnosisState()
    data class Result(val problem: Problem?, val guideId: String?, val path: List<DiagnosisNode>) : DiagnosisState()
    data class Error(val message: String) : DiagnosisState()
}

@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    private val repository: ProblemRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DiagnosisState>(DiagnosisState.Loading)
    val state: StateFlow<DiagnosisState> = _state.asStateFlow()

    private val _path = MutableStateFlow<List<DiagnosisNode>>(emptyList())
    val path: StateFlow<List<DiagnosisNode>> = _path.asStateFlow()

    private var startNodeId: String = ""

    init {
        startDiagnosis()
    }

    fun startDiagnosis() {
        viewModelScope.launch {
            _state.value = DiagnosisState.Loading
            try {
                val startNode = repository.getDiagnosisTree()
                if (startNode != null) {
                    startNodeId = startNode.id
                    _path.value = listOf(startNode)
                    _state.value = DiagnosisState.Question(startNode, listOf(startNode))
                } else {
                    _state.value = DiagnosisState.Error("درخت عیب‌یابی یافت نشد")
                }
            } catch (e: Exception) {
                _state.value = DiagnosisState.Error(e.localizedMessage ?: "خطا در بارگذاری")
            }
        }
    }

    fun onAnswerSelected(node: DiagnosisNode, optionLabel: String) {
        viewModelScope.launch {
            val option = node.options.find { it.label == optionLabel } ?: return@launch

            if (option.problemId != null) {
                val problem = repository.getProblemById(option.problemId)
                val newPath = _path.value + node
                _path.value = newPath
                _state.value = DiagnosisState.Result(problem, option.guideId, newPath)
            } else if (option.nextNodeId != null) {
                val nextNode = repository.getDiagnosisNode(option.nextNodeId)
                if (nextNode != null) {
                    val newPath = _path.value + nextNode
                    _path.value = newPath
                    if (nextNode.isEndNode && nextNode.problemId != null) {
                        val problem = repository.getProblemById(nextNode.problemId)
                        _state.value = DiagnosisState.Result(problem, nextNode.guideId, newPath)
                    } else {
                        _state.value = DiagnosisState.Question(nextNode, newPath)
                    }
                }
            }
        }
    }

    fun goBack() {
        val currentPath = _path.value
        if (currentPath.size > 1) {
            val newPath = currentPath.dropLast(1)
            _path.value = newPath
            val previousNode = newPath.last()
            _state.value = DiagnosisState.Question(previousNode, newPath)
        }
    }

    fun reset() {
        _path.value = emptyList()
        startDiagnosis()
    }
}
