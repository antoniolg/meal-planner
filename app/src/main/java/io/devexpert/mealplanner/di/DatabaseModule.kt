package io.devexpert.mealplanner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.devexpert.mealplanner.data.local.MIGRATION_1_2
import io.devexpert.mealplanner.data.local.MealPlannerDatabase
import io.devexpert.mealplanner.data.local.dao.MealPlanDao
import io.devexpert.mealplanner.data.local.dao.ShoppingItemDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideMealPlannerDatabase(@ApplicationContext context: Context): MealPlannerDatabase {
        return Room.databaseBuilder(
            context,
            MealPlannerDatabase::class.java,
            "meal_planner_database"
        )
        .addMigrations(MIGRATION_1_2)
        .build()
    }
    
    @Provides
    @Singleton
    fun provideMealPlanDao(database: MealPlannerDatabase): MealPlanDao {
        return database.mealPlanDao()
    }
    
    @Provides
    @Singleton
    fun provideShoppingItemDao(database: MealPlannerDatabase): ShoppingItemDao {
        return database.shoppingItemDao()
    }
}
