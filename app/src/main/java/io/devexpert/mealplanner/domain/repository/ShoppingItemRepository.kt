package io.devexpert.mealplanner.domain.repository

import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingItemRepository {
    suspend fun saveShoppingItem(shoppingItem: ShoppingItem): Long
    suspend fun updateShoppingItem(shoppingItem: ShoppingItem)
    fun getAllShoppingItems(): Flow<List<ShoppingItem>>
    suspend fun deleteShoppingItem(itemId: Long)
    suspend fun deleteAllShoppingItems()
    suspend fun generateShoppingListFromMealPlan(mealPlan: MealPlan)
}
