package io.devexpert.mealplanner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.devexpert.mealplanner.data.local.MealPlannerDatabase
import io.devexpert.mealplanner.data.local.dao.MealPlanDao
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
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideMealPlanDao(database: MealPlannerDatabase): MealPlanDao {
        return database.mealPlanDao()
    }
}
