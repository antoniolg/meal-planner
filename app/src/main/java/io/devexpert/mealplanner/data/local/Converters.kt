package io.devexpert.mealplanner.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.devexpert.mealplanner.domain.model.DayPlan
import io.devexpert.mealplanner.domain.model.Ingredient
import io.devexpert.mealplanner.domain.model.Meal
import io.devexpert.mealplanner.domain.model.Recipe

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromDayPlanList(value: List<DayPlan>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toDayPlanList(value: String): List<DayPlan> {
        val listType = object : TypeToken<List<DayPlan>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromMealList(value: List<Meal>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toMealList(value: String): List<Meal> {
        val listType = object : TypeToken<List<Meal>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toIngredientList(value: String): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromRecipe(value: Recipe): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toRecipe(value: String): Recipe {
        return gson.fromJson(value, Recipe::class.java)
    }
    
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}
