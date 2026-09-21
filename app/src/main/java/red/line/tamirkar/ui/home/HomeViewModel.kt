package red.line.tamirkar.ui.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import red.line.tamirkar.data.repository.UserPreferencesRepository
import red.line.tamirkar.domain.model.RotaryMenuItem
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefsRepository: UserPreferencesRepository
) : ViewModel() {

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

    private val _rotationAngle = MutableStateFlow(0f)
    val rotationAngle: StateFlow<Float> = _rotationAngle.asStateFlow()

    private val _isDragging = MutableStateFlow(false)
    val isDragging: StateFlow<Boolean> = _isDragging.asStateFlow()

    val menuItems = listOf(
        RotaryMenuItem(id = "troubleshoot", label = "عیب‌یابی", icon = null, angleDegrees = 270f, color = Color(0xFF00E5FF), route = "problem_list"),
        RotaryMenuItem(id = "brands", label = "برندها", icon = null, angleDegrees = 315f, color = Color(0xFFFFD740), route = "brands"),
        RotaryMenuItem(id = "power_diag", label = "جریان‌کشی", icon = null, angleDegrees = 0f, color = Color(0xFF69F0AE), route = "power_diagnostics"),
        RotaryMenuItem(id = "secret_codes", label = "کدهای مخفی", icon = null, angleDegrees = 45f, color = Color(0xFFE040FB), route = "secret_codes"),
        RotaryMenuItem(id = "backup", label = "پشتیبان‌گیری", icon = null, angleDegrees = 90f, color = Color(0xFFFFAB40), route = "backup"),
        RotaryMenuItem(id = "customer", label = "مشتریان", icon = null, angleDegrees = 135f, color = Color(0xFFEF5350), route = "customer"),
        RotaryMenuItem(id = "schematics", label = "نقشه‌خوانی", icon = null, angleDegrees = 180f, color = Color(0xFF26C6DA), route = "schematics"),
        RotaryMenuItem(id = "models", label = "مدل‌ها", icon = null, angleDegrees = 225f, color = Color(0xFFAB47BC), route = "models")
    )

    val selectedItem: StateFlow<RotaryMenuItem?> = _selectedIndex.map { index ->
        menuItems.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), menuItems.first())

    val isRotaryMenu: StateFlow<Boolean> = prefsRepository.isRotaryMenu
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleMenuType(isRotary: Boolean) {
        viewModelScope.launch {
            prefsRepository.setRotaryMenu(isRotary)
        }
    }

    fun onDragStart() { _isDragging.value = true }
    
    fun onDragEnd() {
        _isDragging.value = false
        snapToNearestItem()
    }

    fun onRotationDelta(deltaAngle: Float) {
        _rotationAngle.value = (_rotationAngle.value + deltaAngle) % 360f
        updateSelectedIndexFromRotation()
    }

    private fun updateSelectedIndexFromRotation() {
        val angle = (_rotationAngle.value + 360f) % 360f
        val segmentSize = 360f / menuItems.size
        val index = ((angle + segmentSize / 2) / segmentSize).toInt() % menuItems.size
        if (_selectedIndex.value != index) _selectedIndex.value = index
    }

    private fun snapToNearestItem() {
        val segmentSize = 360f / menuItems.size
        val currentAngle = (_rotationAngle.value + 360f) % 360f
        val nearestIndex = ((currentAngle + segmentSize / 2) / segmentSize).toInt() % menuItems.size
        viewModelScope.launch {
            _rotationAngle.value = nearestIndex * segmentSize
            _selectedIndex.value = nearestIndex
        }
    }

    fun selectItem(index: Int) {
        if (index in menuItems.indices) {
            val segmentSize = 360f / menuItems.size
            _selectedIndex.value = index
            _rotationAngle.value = index * segmentSize
        }
    }
}