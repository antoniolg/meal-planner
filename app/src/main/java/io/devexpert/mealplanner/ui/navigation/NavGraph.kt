package io.devexpert.mealplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.devexpert.mealplanner.domain.model.DayPlan
import io.devexpert.mealplanner.domain.model.Meal
import io.devexpert.mealplanner.ui.screens.ChatScreen
import io.devexpert.mealplanner.ui.screens.MealDetailScreen
import io.devexpert.mealplanner.ui.screens.MealPlanScreen
import io.devexpert.mealplanner.ui.viewmodel.ChatViewModel

object Destinations {
    const val CHAT_SCREEN = "chat"
    const val MEAL_PLAN_SCREEN = "meal_plan"
    const val MEAL_DETAIL_SCREEN = "meal_detail/{dayIndex}/{mealIndex}"
    
    fun mealDetailRoute(dayIndex: Int, mealIndex: Int): String {
        return "meal_detail/$dayIndex/$mealIndex"
    }
}

@Composable
fun MealPlannerNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.CHAT_SCREEN
) {
    val viewModel: ChatViewModel = hiltViewModel()
    
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Destinations.CHAT_SCREEN) {
            ChatScreen(
                viewModel = viewModel,
                onMealPlanGenerated = {
                    navController.navigate(Destinations.MEAL_PLAN_SCREEN)
                }
            )
        }
        
        composable(Destinations.MEAL_PLAN_SCREEN) {
            MealPlanScreen(
                viewModel = viewModel,
                onMealClick = { dayPlan, meal ->
                    val dayIndex = viewModel.uiState.value.mealPlan?.days?.indexOf(dayPlan) ?: 0
                    val mealIndex = dayPlan.meals.indexOf(meal)
                    navController.navigate(Destinations.mealDetailRoute(dayIndex, mealIndex))
                }
            )
        }
        
        composable(
            route = Destinations.MEAL_DETAIL_SCREEN,
            arguments = listOf(
                navArgument("dayIndex") { type = NavType.IntType },
                navArgument("mealIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 0
            val mealIndex = backStackEntry.arguments?.getInt("mealIndex") ?: 0
            
            val dayPlan = remember {
                viewModel.uiState.value.mealPlan?.days?.getOrNull(dayIndex) ?: DayPlan("", emptyList())
            }
            
            val meal = remember {
                dayPlan.meals.getOrNull(mealIndex) ?: Meal("", recipe = io.devexpert.mealplanner.domain.model.Recipe(), ingredients = emptyList())
            }
            
            MealDetailScreen(
                dayPlan = dayPlan,
                meal = meal,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
