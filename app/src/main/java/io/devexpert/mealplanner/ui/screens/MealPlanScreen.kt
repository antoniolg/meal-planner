package io.devexpert.mealplanner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.devexpert.mealplanner.R
import io.devexpert.mealplanner.domain.model.DayPlan
import io.devexpert.mealplanner.domain.model.Meal
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.ui.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
    onMealClick: (DayPlan, Meal) -> Unit,
    onNewPlanClick: () -> Unit = {},
    onShoppingListClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.meal_plan_title)) },
                actions = {
                    IconButton(onClick = onShoppingListClick) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(R.string.shopping_list)
                        )
                    }
                    IconButton(onClick = onNewPlanClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Crear nuevo plan"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            uiState.mealPlan?.let { mealPlan ->
                MealPlanContent(
                    mealPlan = mealPlan,
                    onMealClick = onMealClick
                )
            } ?: run {
                // Si no hay plan de comidas, mostrar un mensaje
                EmptyState(onNewPlanClick = onNewPlanClick)
            }
        }
    }
}

@Composable
fun MealPlanContent(
    mealPlan: MealPlan,
    onMealClick: (DayPlan, Meal) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(mealPlan.days) { dayPlan ->
            DayCard(
                dayPlan = dayPlan,
                onMealClick = { meal -> onMealClick(dayPlan, meal) }
            )
        }
    }
}

@Composable
fun DayCard(
    dayPlan: DayPlan,
    onMealClick: (Meal) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Título del día
            Text(
                text = dayPlan.day,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Lista de comidas
            dayPlan.meals.forEach { meal ->
                MealItem(
                    meal = meal,
                    onClick = { onMealClick(meal) }
                )
                
                if (meal != dayPlan.meals.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}

@Composable
fun MealItem(
    meal: Meal,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo de color para hacer más visual
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Nombre de la comida
        Text(
            text = meal.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        // Icono para indicar que se puede hacer clic
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.view_details),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun EmptyState(onNewPlanClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No meal plan available yet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Create a new meal plan",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onNewPlanClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Create New Plan")
        }
    }
}
