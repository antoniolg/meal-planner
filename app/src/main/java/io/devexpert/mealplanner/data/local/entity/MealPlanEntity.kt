package io.devexpert.mealplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.devexpert.mealplanner.domain.model.DayPlan
import io.devexpert.mealplanner.domain.model.MealPlan

@Entity(tableName = "meal_plans")
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val days: List<DayPlan>,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): MealPlan {
        return MealPlan(days = days)
    }
    
    companion object {
        fun fromDomainModel(mealPlan: MealPlan): MealPlanEntity {
            return MealPlanEntity(
                days = mealPlan.days,
                createdAt = System.currentTimeMillis()
            )
        }
    }
}
