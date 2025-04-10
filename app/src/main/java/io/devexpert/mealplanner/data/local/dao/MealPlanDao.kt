package io.devexpert.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.devexpert.mealplanner.data.local.entity.MealPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlan(mealPlan: MealPlanEntity): Long
    
    @Query("SELECT * FROM meal_plans ORDER BY createdAt DESC LIMIT 1")
    fun getLatestMealPlan(): Flow<MealPlanEntity?>
    
    @Query("SELECT * FROM meal_plans ORDER BY createdAt DESC")
    fun getAllMealPlans(): Flow<List<MealPlanEntity>>
    
    @Query("DELETE FROM meal_plans")
    suspend fun deleteAllMealPlans()
    
    @Query("DELETE FROM meal_plans WHERE id = :id")
    suspend fun deleteMealPlan(id: Long)
    
    @Query("SELECT EXISTS(SELECT 1 FROM meal_plans LIMIT 1)")
    fun hasMealPlan(): Flow<Boolean>
}
