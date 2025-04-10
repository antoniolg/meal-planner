package io.devexpert.mealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.devexpert.mealplanner.data.local.dao.MealPlanDao
import io.devexpert.mealplanner.data.local.entity.MealPlanEntity

@Database(
    entities = [MealPlanEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MealPlannerDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao
}
