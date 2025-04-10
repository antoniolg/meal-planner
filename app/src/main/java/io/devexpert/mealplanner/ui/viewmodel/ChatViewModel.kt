package io.devexpert.mealplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val mealPlanRepository: MealPlanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun generateMealPlan(prompt: String) {
        if (prompt.isBlank()) return
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            mealPlanRepository.generateMealPlan(prompt).collect { result ->
                result.fold(
                    onSuccess = { mealPlan ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                mealPlan = mealPlan
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                error = error.localizedMessage
                            )
                        }
                    }
                )
            }
        }
    }
}

data class ChatUiState(
    val isLoading: Boolean = false,
    val mealPlan: MealPlan? = null,
    val error: String? = null
)
