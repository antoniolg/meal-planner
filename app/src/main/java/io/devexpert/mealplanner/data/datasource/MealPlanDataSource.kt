package io.devexpert.mealplanner.data.datasource

import io.devexpert.mealplanner.domain.model.MealPlan
import kotlinx.coroutines.flow.Flow

interface MealPlanDataSource {
    suspend fun generateMealPlan(prompt: String): Flow<Result<MealPlan>>
}
