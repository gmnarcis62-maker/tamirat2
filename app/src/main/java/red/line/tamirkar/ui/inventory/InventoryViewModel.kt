package red.line.tamirkar.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import red.line.tamirkar.domain.model.InventoryCategory
import red.line.tamirkar.domain.model.InventoryItem
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

sealed class InventoryUiState {
    object Loading : InventoryUiState()
    data class Success(
        val items: List<InventoryItem>,
        val filterCategory: InventoryCategory? = null,
        val showLowStockOnly: Boolean = false,
        val searchQuery: String = "",
        val stats: InventoryStats = InventoryStats()
    ) : InventoryUiState()
    data class Error(val message: String) : InventoryUiState()
}

data class InventoryStats(
    val totalItems: Int = 0,
    val totalValue: Double = 0.0,
    val lowStockCount: Int = 0,
    val outOfStockCount: Int = 0
)

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val repository: RepairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<InventoryUiState>(InventoryUiState.Loading)
    val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()

    private var allItems: List<InventoryItem> = emptyList()

    init {
        loadInventory()
    }

    private fun loadInventory() {
        viewModelScope.launch {
            _uiState.value = InventoryUiState.Loading
            try {
                allItems = repository.getAllInventoryItems()
                val stats = calculateStats(allItems)
                _uiState.value = InventoryUiState.Success(
                    items = allItems,
                    stats = stats
                )
            } catch (e: Exception) {
                _uiState.value = InventoryUiState.Error("خطا در دریافت لیست انبار: ${e.localizedMessage}")
            }
        }
    }

    private fun calculateStats(items: List<InventoryItem>): InventoryStats {
        val totalValue = items.sumOf { (it.purchasePrice ?: 0.0) * it.quantity }
        val lowStock = items.count { it.isLowStock }
        val outOfStock = items.count { it.quantity == 0 }
        return InventoryStats(
            totalItems = items.size,
            totalValue = totalValue,
            lowStockCount = lowStock,
            outOfStockCount = outOfStock
        )
    }

    fun search(query: String) {
        applyFilters(searchQuery = query)
    }

    fun filterByCategory(category: InventoryCategory?) {
        val current = _uiState.value as? InventoryUiState.Success ?: return
        applyFilters(
            filterCategory = category,
            showLowStockOnly = current.showLowStockOnly,
            searchQuery = current.searchQuery
        )
    }

    fun toggleLowStockFilter(showOnly: Boolean) {
        val current = _uiState.value as? InventoryUiState.Success ?: return
        applyFilters(
            filterCategory = current.filterCategory,
            showLowStockOnly = showOnly,
            searchQuery = current.searchQuery
        )
    }

    private fun applyFilters(
        filterCategory: InventoryCategory? = null,
        showLowStockOnly: Boolean = false,
        searchQuery: String = ""
    ) {
        val current = _uiState.value as? InventoryUiState.Success ?: return

        var filtered = allItems

        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.partNumber?.contains(searchQuery, ignoreCase = true) == true ||
                it.supplier?.contains(searchQuery, ignoreCase = true) == true
            }
        }

        if (filterCategory != null) {
            filtered = filtered.filter { it.category == filterCategory.name }
        }

        if (showLowStockOnly) {
            filtered = filtered.filter { it.isLowStock || it.quantity == 0 }
        }

        _uiState.value = current.copy(
            items = filtered,
            filterCategory = filterCategory,
            showLowStockOnly = showLowStockOnly,
            searchQuery = searchQuery
        )
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            try {
                repository.deleteInventoryItem(itemId)
                loadInventory()
            } catch (e: Exception) {
                _uiState.value = InventoryUiState.Error("خطا در حذف قطعه")
            }
        }
    }

    fun refresh() {
        loadInventory()
    }
}
