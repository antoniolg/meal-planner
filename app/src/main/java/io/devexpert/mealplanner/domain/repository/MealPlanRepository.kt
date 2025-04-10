package io.devexpert.mealplanner.domain.repository

import io.devexpert.mealplanner.domain.model.MealPlan
import kotlinx.coroutines.flow.Flow

interface MealPlanRepository {
    suspend fun generateMealPlan(prompt: String): Flow<Result<MealPlan>>
    
    fun getLatestMealPlan(): Flow<MealPlan?>
    
    fun getAllMealPlans(): Flow<List<MealPlan>>
    
    fun hasMealPlan(): Flow<Boolean>
    
    suspend fun deleteMealPlan(id: Long)
    
    suspend fun deleteAllMealPlans()
}
