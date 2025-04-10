package io.devexpert.mealplanner.data.datasource

import io.devexpert.mealplanner.data.local.dao.MealPlanDao
import io.devexpert.mealplanner.data.local.entity.MealPlanEntity
import io.devexpert.mealplanner.domain.model.MealPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMealPlanDataSource @Inject constructor(
    private val mealPlanDao: MealPlanDao
) {
    suspend fun saveMealPlan(mealPlan: MealPlan): Long {
        return mealPlanDao.insertMealPlan(MealPlanEntity.fromDomainModel(mealPlan))
    }
    
    fun getLatestMealPlan(): Flow<MealPlan?> {
        return mealPlanDao.getLatestMealPlan().map { entity ->
            entity?.toDomainModel()
        }
    }
    
    fun getAllMealPlans(): Flow<List<MealPlan>> {
        return mealPlanDao.getAllMealPlans().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    suspend fun deleteAllMealPlans() {
        mealPlanDao.deleteAllMealPlans()
    }
    
    suspend fun deleteMealPlan(id: Long) {
        mealPlanDao.deleteMealPlan(id)
    }
    
    fun hasMealPlan(): Flow<Boolean> {
        return mealPlanDao.hasMealPlan()
    }
}
