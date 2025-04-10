package io.devexpert.mealplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.model.ShoppingItem
import io.devexpert.mealplanner.domain.repository.MealPlanRepository
import io.devexpert.mealplanner.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val mealPlanRepository: MealPlanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val uiState: StateFlow<ShoppingListUiState> = _uiState.asStateFlow()

    init {
        loadShoppingItems()
    }

    private fun loadShoppingItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            shoppingItemRepository.getAllShoppingItems().collect { items ->
                _uiState.update {
                    it.copy(
                        shoppingItems = items,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun toggleItemChecked(item: ShoppingItem) {
        viewModelScope.launch {
            val updatedItem = item.copy(isChecked = !item.isChecked)
            shoppingItemRepository.updateShoppingItem(updatedItem)
        }
    }

    fun deleteItem(itemId: Long) {
        viewModelScope.launch {
            shoppingItemRepository.deleteShoppingItem(itemId)
        }
    }

    fun generateShoppingListFromMealPlan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                // Obtener el plan de comidas más reciente
                val mealPlan = mealPlanRepository.getLatestMealPlan().first()
                
                // Si hay un plan de comidas, generar la lista de compra
                mealPlan?.let {
                    shoppingItemRepository.generateShoppingListFromMealPlan(it)
                }
            } catch (e: Exception) {
                // Manejar el error
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun clearShoppingList() {
        viewModelScope.launch {
            shoppingItemRepository.deleteAllShoppingItems()
        }
    }
}

data class ShoppingListUiState(
    val shoppingItems: List<ShoppingItem> = emptyList(),
    val isLoading: Boolean = false
)
