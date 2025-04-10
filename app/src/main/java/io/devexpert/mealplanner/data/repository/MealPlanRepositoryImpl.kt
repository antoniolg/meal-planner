package io.devexpert.mealplanner.data.repository

import io.devexpert.mealplanner.data.datasource.MealPlanDataSource
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealPlanRepositoryImpl @Inject constructor(
    private val dataSource: MealPlanDataSource
) : MealPlanRepository {
    
    override suspend fun generateMealPlan(prompt: String): Flow<Result<MealPlan>> {
        return dataSource.generateMealPlan(prompt)
    }
}
