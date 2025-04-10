package io.devexpert.mealplanner.data.repository

import io.devexpert.mealplanner.data.datasource.LocalShoppingItemDataSource
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.model.ShoppingItem
import io.devexpert.mealplanner.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingItemRepositoryImpl @Inject constructor(
    private val localDataSource: LocalShoppingItemDataSource
) : ShoppingItemRepository {
    
    override suspend fun saveShoppingItem(shoppingItem: ShoppingItem): Long {
        return localDataSource.saveShoppingItem(shoppingItem)
    }
    
    override suspend fun updateShoppingItem(shoppingItem: ShoppingItem) {
        localDataSource.updateShoppingItem(shoppingItem)
    }
    
    override fun getAllShoppingItems(): Flow<List<ShoppingItem>> {
        return localDataSource.getAllShoppingItems()
    }
    
    override suspend fun deleteShoppingItem(itemId: Long) {
        localDataSource.deleteShoppingItem(itemId)
    }
    
    override suspend fun deleteAllShoppingItems() {
        localDataSource.deleteAllShoppingItems()
    }
    
    override suspend fun generateShoppingListFromMealPlan(mealPlan: MealPlan) {
        localDataSource.generateShoppingListFromMealPlan(mealPlan)
    }
}
