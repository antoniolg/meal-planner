package io.devexpert.mealplanner.data.repository

import io.devexpert.mealplanner.data.datasource.LocalMealPlanDataSource
import io.devexpert.mealplanner.data.datasource.MealPlanDataSource
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealPlanRepositoryImpl @Inject constructor(
    private val remoteDataSource: MealPlanDataSource,
    private val localDataSource: LocalMealPlanDataSource
) : MealPlanRepository {
    
    override suspend fun generateMealPlan(prompt: String): Flow<Result<MealPlan>> {
        return remoteDataSource.generateMealPlan(prompt).onEach { result ->
            // Si la generación fue exitosa, guardar en la base de datos
            if (result.isSuccess) {
                result.getOrNull()?.let { mealPlan ->
                    localDataSource.saveMealPlan(mealPlan)
                }
            }
        }
    }
    
    override fun getLatestMealPlan(): Flow<MealPlan?> {
        return localDataSource.getLatestMealPlan()
    }
    
    override fun getAllMealPlans(): Flow<List<MealPlan>> {
        return localDataSource.getAllMealPlans()
    }
    
    override fun hasMealPlan(): Flow<Boolean> {
        return localDataSource.hasMealPlan()
    }
    
    override suspend fun deleteMealPlan(id: Long) {
        localDataSource.deleteMealPlan(id)
    }
    
    override suspend fun deleteAllMealPlans() {
        localDataSource.deleteAllMealPlans()
    }
}
