package io.devexpert.mealplanner.domain.model

data class MealPlan(
    val days: List<DayPlan> = emptyList()
)

data class DayPlan(
    val day: String,
    val meals: List<Meal> = emptyList()
)

data class Meal(
    val name: String,
    val recipe: Recipe,
    val ingredients: List<Ingredient> = emptyList()
)

data class Recipe(
    val steps: List<String> = emptyList()
)

data class Ingredient(
    val name: String,
    val quantity: String
)
