package io.devexpert.mealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.devexpert.mealplanner.data.local.dao.MealPlanDao
import io.devexpert.mealplanner.data.local.dao.ShoppingItemDao
import io.devexpert.mealplanner.data.local.entity.MealPlanEntity
import io.devexpert.mealplanner.data.local.entity.ShoppingItemEntity

@Database(
    entities = [MealPlanEntity::class, ShoppingItemEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MealPlannerDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}
