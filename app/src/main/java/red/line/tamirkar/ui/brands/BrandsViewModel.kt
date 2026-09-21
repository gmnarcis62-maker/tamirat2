package red.line.tamirkar.ui.brands

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import red.line.tamirkar.data.local.seed.SeedData
import javax.inject.Inject

data class BrandItem(
    val id: String,
    val name: String,
    val nameEn: String,
    val slug: String,
    val logo: String?,
    val modelCount: Int = 0
)

@HiltViewModel
class BrandsViewModel @Inject constructor(
    private val seedData: SeedData
) : ViewModel() {

    private val _brands = MutableStateFlow<List<BrandItem>>(emptyList())
    val brands: StateFlow<List<BrandItem>> = _brands.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadBrands()
    }

    private fun loadBrands() {
        viewModelScope.launch {
            _isLoading.value = true
            val allModels = seedData.models()
            _brands.value = seedData.brands().map { brand ->
                BrandItem(
                    id = brand.id,
                    name = brand.name,
                    nameEn = brand.nameEn,
                    slug = brand.slug,
                    logo = brand.logo,
                    modelCount = allModels.count { it.brandId == brand.id }
                )
            }
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            val allBrands = seedData.brands()
            val allModels = seedData.models()
            val filtered = if (query.isBlank()) {
                allBrands
            } else {
                allBrands.filter { brand ->
                    brand.name.contains(query, ignoreCase = true) ||
                    brand.nameEn.contains(query, ignoreCase = true) ||
                    brand.slug.contains(query, ignoreCase = true)
                }
            }
            _brands.value = filtered.map { brand ->
                BrandItem(
                    id = brand.id,
                    name = brand.name,
                    nameEn = brand.nameEn,
                    slug = brand.slug,
                    logo = brand.logo,
                    modelCount = allModels.count { it.brandId == brand.id }
                )
            }
        }
    }
}