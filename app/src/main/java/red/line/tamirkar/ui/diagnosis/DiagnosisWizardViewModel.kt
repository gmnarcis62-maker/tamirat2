package red.line.tamirkar.ui.diagnosis

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.core.result.RepairResult
import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.DiagnosisOptionEntity
import red.line.tamirkar.data.local.entity.DiagnosisSessionEntity
import red.line.tamirkar.domain.diagnosis.DiagnosisEngine
import javax.inject.Inject

sealed class DiagnosisUiState {
    object Loading : DiagnosisUiState()
    data class Question(
        val node: DiagnosisNodeEntity,
        val options: List<DiagnosisOptionEntity>,
        val canGoBack: Boolean,
        val progress: Float
    ) : DiagnosisUiState()
    data class Result(
        val session: DiagnosisSessionEntity,
        val message: String,
        val recommendations: List<String>
    ) : DiagnosisUiState()
    data class Error(val message: String) : DiagnosisUiState()
}

@HiltViewModel
class DiagnosisWizardViewModel @Inject constructor(
    private val diagnosisEngine: DiagnosisEngine,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val problemId: String = savedStateHandle["problemId"] ?: ""
    private val modelId: String? = savedStateHandle["modelId"]

    private val _uiState = MutableStateFlow<DiagnosisUiState>(DiagnosisUiState.Loading)
    val uiState: StateFlow<DiagnosisUiState> = _uiState.asStateFlow()

    private var sessionId: String? = null
    private val _history = MutableStateFlow<List<String>>(emptyList())

    init {
        startDiagnosis()
    }

    private fun startDiagnosis() {
        viewModelScope.launch {
            _uiState.value = DiagnosisUiState.Loading
            try {
                val sid = diagnosisEngine.createSession(problemId, modelId)
                sessionId = sid
                loadCurrentNode(sid)
            } catch (e: Exception) {
                _uiState.value = DiagnosisUiState.Error("خطا در شروع عیب‌یابی: ${e.localizedMessage}")
            }
        }
    }

    private suspend fun loadCurrentNode(sid: String) {
        val node = diagnosisEngine.getCurrentNode(sid)
        if (node == null) {
            val session = diagnosisEngine.getSession(sid)
            if (session != null && session.status == "COMPLETED") {
                _uiState.value = DiagnosisUiState.Result(
                    session = session,
                    message = "عیب‌یابی به پایان رسید. بر اساس پاسخ‌های شما، موارد زیر محتمل هستند:",
                    recommendations = listOf(
                        "نتیجه‌ها بر اساس داده‌های واردشده هستند و تشخیص قطعی نیستند.",
                        "تست‌های فیزیکی و بررسی با ابزارهای تخصصی انجام دهید.",
                        "در صورت تردید، از بخش AI تعمیرکار کمک بگیرید."
                    )
                )
            } else {
                _uiState.value = DiagnosisUiState.Error("نشست عیب‌یابی نامعتبر است.")
            }
            return
        }

        val options = diagnosisEngine.getOptions(node.id)
        val history = _history.value
        val progress = if (history.isEmpty()) 0.1f else (history.size.toFloat() / (history.size + 3)).coerceIn(0.1f, 0.95f)

        _uiState.value = DiagnosisUiState.Question(
            node = node,
            options = options,
            canGoBack = history.isNotEmpty(),
            progress = progress
        )
    }

    fun selectOption(optionId: String) {
        val currentSid = sessionId ?: return
        viewModelScope.launch {
            _uiState.value = DiagnosisUiState.Loading
            try {
                diagnosisEngine.submitAnswer(currentSid, optionId)
                _history.value = _history.value + optionId
                loadCurrentNode(currentSid)
            } catch (e: Exception) {
                _uiState.value = DiagnosisUiState.Error("خطا در ثبت پاسخ: ${e.localizedMessage}")
            }
        }
    }

    fun goBack() {
        val currentSid = sessionId ?: return
        viewModelScope.launch {
            _uiState.value = DiagnosisUiState.Loading
            try {
                diagnosisEngine.goBack(currentSid)
                _history.value = _history.value.dropLast(1)
                loadCurrentNode(currentSid)
            } catch (e: Exception) {
                _uiState.value = DiagnosisUiState.Error("خطا در بازگشت: ${e.localizedMessage}")
            }
        }
    }

    fun restart() {
        _history.value = emptyList()
        startDiagnosis()
    }
}
