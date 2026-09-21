package red.line.tamirkar.ui.guide

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.data.local.seed.ProblemSeedData
import red.line.tamirkar.domain.model.RepairGuide
import red.line.tamirkar.domain.model.RepairStep
import red.line.tamirkar.domain.model.Tool
import red.line.tamirkar.domain.model.Part
import javax.inject.Inject

@HiltViewModel
class RepairGuideViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val problemId: String = savedStateHandle["problemId"] ?: ""

    private val _guide = MutableStateFlow<RepairGuide?>(null)
    val guide: StateFlow<RepairGuide?> = _guide.asStateFlow()

    private val _currentStepIndex = MutableStateFlow(0)
    val currentStepIndex: StateFlow<Int> = _currentStepIndex.asStateFlow()

    private val _completedSteps = MutableStateFlow<Set<Int>>(emptySet())
    val completedSteps: StateFlow<Set<Int>> = _completedSteps.asStateFlow()

    private val _checkedCheckpoints = MutableStateFlow<Map<Int, Set<String>>>(emptyMap())
    val checkedCheckpoints: StateFlow<Map<Int, Set<String>>> = _checkedCheckpoints.asStateFlow()

    private val _showTools = MutableStateFlow(false)
    val showTools: StateFlow<Boolean> = _showTools.asStateFlow()

    private val _showParts = MutableStateFlow(false)
    val showParts: StateFlow<Boolean> = _showParts.asStateFlow()

    private val _showWarnings = MutableStateFlow(false)
    val showWarnings: StateFlow<Boolean> = _showWarnings.asStateFlow()

    private val _showTips = MutableStateFlow(false)
    val showTips: StateFlow<Boolean> = _showTips.asStateFlow()

    val currentStep: StateFlow<RepairStep?> = combine(_guide, _currentStepIndex) { g, idx ->
        g?.steps?.getOrNull(idx)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val progress: StateFlow<Float> = combine(_guide, _completedSteps) { g, completed ->
        if (g == null || g.steps.isEmpty()) 0f
        else completed.size.toFloat() / g.steps.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    val isLastStep: StateFlow<Boolean> = combine(_guide, _currentStepIndex) { g, idx ->
        g != null && idx >= g.steps.size - 1
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        loadGuide()
    }

    private fun loadGuide() {
        val allProblems = ProblemSeedData.getAllProblems()
        _guide.value = allProblems.find { it.id == problemId }?.repairGuide
    }

    fun goToStep(index: Int) {
        val totalSteps = _guide.value?.steps?.size ?: 0
        _currentStepIndex.value = index.coerceIn(0, (totalSteps - 1).coerceAtLeast(0))
    }

    fun nextStep() {
        goToStep(_currentStepIndex.value + 1)
    }

    fun previousStep() {
        goToStep(_currentStepIndex.value - 1)
    }

    fun toggleStepComplete(stepIndex: Int) {
        _completedSteps.value = _completedSteps.value.toMutableSet().apply {
            if (contains(stepIndex)) remove(stepIndex) else add(stepIndex)
        }
    }

    fun toggleCheckpoint(stepIndex: Int, checkpoint: String) {
        _checkedCheckpoints.value = _checkedCheckpoints.value.toMutableMap().apply {
            val current = getOrDefault(stepIndex, emptySet()).toMutableSet()
            if (current.contains(checkpoint)) current.remove(checkpoint) else current.add(checkpoint)
            put(stepIndex, current)
        }
    }

    fun isCheckpointChecked(stepIndex: Int, checkpoint: String): Boolean {
        return _checkedCheckpoints.value[stepIndex]?.contains(checkpoint) ?: false
    }

    fun toggleTools() { _showTools.value = !_showTools.value }
    fun toggleParts() { _showParts.value = !_showParts.value }
    fun toggleWarnings() { _showWarnings.value = !_showWarnings.value }
    fun toggleTips() { _showTips.value = !_showTips.value }

    fun markAllComplete() {
        _guide.value?.steps?.indices?.let { indices ->
            _completedSteps.value = indices.toSet()
        }
    }

    fun resetProgress() {
        _completedSteps.value = emptySet()
        _checkedCheckpoints.value = emptyMap()
        _currentStepIndex.value = 0
    }
}
