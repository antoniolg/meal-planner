package io.devexpert.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.devexpert.mealplanner.data.local.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(shoppingItems: List<ShoppingItemEntity>)
    
    @Update
    suspend fun updateShoppingItem(shoppingItem: ShoppingItemEntity)
    
    @Query("SELECT * FROM shopping_items ORDER BY createdAt ASC")
    fun getAllShoppingItems(): Flow<List<ShoppingItemEntity>>
    
    @Query("DELETE FROM shopping_items WHERE id = :itemId")
    suspend fun deleteShoppingItem(itemId: Long)
    
    @Query("DELETE FROM shopping_items")
    suspend fun deleteAllShoppingItems()
}
