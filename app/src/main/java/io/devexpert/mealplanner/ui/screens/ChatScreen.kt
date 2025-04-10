package io.devexpert.mealplanner.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.devexpert.mealplanner.R
import io.devexpert.mealplanner.ui.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .heightIn(min = 150.dp, max = 200.dp),
                label = { Text(stringResource(R.string.describe_meal_plan)) },
                placeholder = { Text(stringResource(R.string.meal_plan_hint)) },
                minLines = 4,
                maxLines = 5,
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (message.isNotBlank()) {
                        viewModel.generateMealPlan(message)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                enabled = message.isNotBlank() && !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(R.string.generate_plan))
                }
            }
            
            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Mostrar un indicador de carga mientras se genera el plan
            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Generando plan de comidas...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Si tenemos un plan de comidas, mostrar un mensaje de éxito
            uiState.mealPlan?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡Plan generado con éxito!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            LaunchedEffect(uiState.mealPlan) {
                if(uiState.mealPlan != null) {
                    Log.d("ChatScreen", uiState.mealPlan.toString())
                }
            }
        }
    }
}
