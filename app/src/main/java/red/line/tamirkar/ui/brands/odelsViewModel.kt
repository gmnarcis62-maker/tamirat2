package red.line.tamirkar.ui.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import red.line.tamirkar.data.local.entity.DeviceModelEntity
import red.line.tamirkar.data.local.seed.SeedData
import javax.inject.Inject

@HiltViewModel
class ModelsViewModel @Inject constructor(
    private val seedData: SeedData,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val brandId: String? = savedStateHandle["brandId"]

    private val _models = MutableStateFlow<List<DeviceModelEntity>>(emptyList())
    val models: StateFlow<List<DeviceModelEntity>> = _models.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadModels()
    }

    private fun loadModels() {
        viewModelScope.launch {
            _isLoading.value = true
            val all = seedData.models()
            _models.value = if (brandId.isNullOrBlank()) all
            else all.filter { it.brandId == brandId }
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            val all = seedData.models()
            val base = if (brandId.isNullOrBlank()) all
            else all.filter { it.brandId == brandId }
            _models.value = if (query.isBlank()) base
            else base.filter { model ->
                model.name.contains(query, ignoreCase = true) ||
                model.nameEn.contains(query, ignoreCase = true) ||
                model.modelNumber.contains(query, ignoreCase = true) ||
                model.normalizedName.contains(query, ignoreCase = true)
            }
        }
    }
}