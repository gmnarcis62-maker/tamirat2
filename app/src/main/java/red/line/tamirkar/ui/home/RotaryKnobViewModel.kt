package red.line.tamirkar.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.RotaryMenuItem
import javax.inject.Inject

@HiltViewModel
class RotaryKnobViewModel @Inject constructor() : ViewModel() {

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

    private val _rotationAngle = MutableStateFlow(0f)
    val rotationAngle: StateFlow<Float> = _rotationAngle.asStateFlow()

    private val _isDragging = MutableStateFlow(false)
    val isDragging: StateFlow<Boolean> = _isDragging.asStateFlow()

    val menuItems = listOf(
        RotaryMenuItem("troubleshoot", "عیب‌یابی", null, 270f, androidx.compose.ui.graphics.Color(0xFF00E5FF), "diagnosis"),
        RotaryMenuItem("schematics", "نقشهخوانی", null, 330f, androidx.compose.ui.graphics.Color(0xFFFFD740), "schematics"),
        RotaryMenuItem("power_diag", "جریان‌کشی", null, 30f, androidx.compose.ui.graphics.Color(0xFF69F0AE), "power_diagnostics"),
        RotaryMenuItem("component_test", "تست قطعات", null, 90f, androidx.compose.ui.graphics.Color(0xFFFF4081), "component_tester"),
        RotaryMenuItem("pinouts", "پین‌اوت‌ها", null, 150f, androidx.compose.ui.graphics.Color(0xFFE040FB), "pinouts"),
        RotaryMenuItem("secret_codes", "کدهای مخفی", null, 210f, androidx.compose.ui.graphics.Color(0xFFFFAB40), "secret_codes")
    )

    val selectedItem: StateFlow<RotaryMenuItem?> = _selectedIndex.map { index ->
        menuItems.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), menuItems.first())

    fun onDragStart() {
        _isDragging.value = true
    }

    fun onDragEnd() {
        _isDragging.value = false
        snapToNearestItem()
    }

    fun onRotationDelta(deltaAngle: Float) {
        _rotationAngle.value = (_rotationAngle.value + deltaAngle) % 360f
        updateSelectedIndexFromRotation()
    }

    fun setRotationAngle(angle: Float) {
        _rotationAngle.value = angle % 360f
        updateSelectedIndexFromRotation()
    }

    private fun updateSelectedIndexFromRotation() {
        val angle = (_rotationAngle.value + 360f) % 360f
        val segmentSize = 360f / menuItems.size
        val index = ((angle + segmentSize / 2) / segmentSize).toInt() % menuItems.size
        if (_selectedIndex.value != index) {
            _selectedIndex.value = index
        }
    }

    private fun snapToNearestItem() {
        val segmentSize = 360f / menuItems.size
        val currentAngle = (_rotationAngle.value + 360f) % 360f
        val nearestIndex = ((currentAngle + segmentSize / 2) / segmentSize).toInt() % menuItems.size
        val targetAngle = nearestIndex * segmentSize
        viewModelScope.launch {
            _rotationAngle.value = targetAngle
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
