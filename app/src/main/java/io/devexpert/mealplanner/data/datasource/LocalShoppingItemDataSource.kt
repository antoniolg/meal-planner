package io.devexpert.mealplanner.data.datasource

import io.devexpert.mealplanner.data.local.dao.ShoppingItemDao
import io.devexpert.mealplanner.data.local.entity.ShoppingItemEntity
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalShoppingItemDataSource @Inject constructor(
    private val shoppingItemDao: ShoppingItemDao
) {
    suspend fun saveShoppingItem(shoppingItem: ShoppingItem): Long {
        return shoppingItemDao.insertShoppingItem(ShoppingItemEntity.fromDomainModel(shoppingItem))
    }
    
    suspend fun saveShoppingItems(shoppingItems: List<ShoppingItem>) {
        shoppingItemDao.insertShoppingItems(shoppingItems.map { ShoppingItemEntity.fromDomainModel(it) })
    }
    
    suspend fun updateShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItemDao.updateShoppingItem(ShoppingItemEntity.fromDomainModel(shoppingItem))
    }
    
    fun getAllShoppingItems(): Flow<List<ShoppingItem>> {
        return shoppingItemDao.getAllShoppingItems().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    suspend fun deleteShoppingItem(itemId: Long) {
        shoppingItemDao.deleteShoppingItem(itemId)
    }
    
    suspend fun deleteAllShoppingItems() {
        shoppingItemDao.deleteAllShoppingItems()
    }
    
    suspend fun generateShoppingListFromMealPlan(mealPlan: MealPlan) {
        // Primero eliminamos todos los elementos anteriores
        deleteAllShoppingItems()
        
        // Extraemos todos los ingredientes del plan de comidas
        val ingredients = mealPlan.days.flatMap { dayPlan ->
            dayPlan.meals.flatMap { meal ->
                meal.ingredients
            }
        }
        
        // Agrupamos los ingredientes por nombre para combinar cantidades
        val groupedIngredients = ingredients.groupBy { it.name.lowercase() }
        
        // Convertimos los ingredientes agrupados a elementos de la lista de compra
        val shoppingItems = groupedIngredients.map { (_, ingredients) ->
            val firstIngredient = ingredients.first()
            val quantities = ingredients.map { it.quantity }
            
            // Si hay múltiples cantidades, las combinamos
            val combinedQuantity = if (quantities.size > 1) {
                quantities.joinToString(", ")
            } else {
                firstIngredient.quantity
            }
            
            ShoppingItem(
                name = firstIngredient.name,
                quantity = combinedQuantity,
                isChecked = false
            )
        }
        
        // Guardamos los elementos en la base de datos
        saveShoppingItems(shoppingItems)
    }
}
