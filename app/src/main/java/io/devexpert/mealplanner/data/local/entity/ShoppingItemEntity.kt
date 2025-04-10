package io.devexpert.mealplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.devexpert.mealplanner.domain.model.ShoppingItem

@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val quantity: String,
    val isChecked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): ShoppingItem {
        return ShoppingItem(
            id = id,
            name = name,
            quantity = quantity,
            isChecked = isChecked
        )
    }
    
    companion object {
        fun fromDomainModel(shoppingItem: ShoppingItem): ShoppingItemEntity {
            return ShoppingItemEntity(
                id = shoppingItem.id,
                name = shoppingItem.name,
                quantity = shoppingItem.quantity,
                isChecked = shoppingItem.isChecked
            )
        }
    }
}
