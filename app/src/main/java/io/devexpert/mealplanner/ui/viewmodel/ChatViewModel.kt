package io.devexpert.mealplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.devexpert.mealplanner.domain.model.MealPlan
import io.devexpert.mealplanner.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val mealPlanRepository: MealPlanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    private val _hasMealPlan = MutableStateFlow(false)
    val hasMealPlan: StateFlow<Boolean> = _hasMealPlan.asStateFlow()

    init {
        loadLatestMealPlan()
        checkIfHasMealPlan()
    }
    
    private fun loadLatestMealPlan() {
        viewModelScope.launch {
            mealPlanRepository.getLatestMealPlan().collectLatest { mealPlan ->
                _uiState.update { it.copy(mealPlan = mealPlan) }
            }
        }
    }
    
    private fun checkIfHasMealPlan() {
        viewModelScope.launch {
            mealPlanRepository.hasMealPlan().collectLatest { hasMealPlan ->
                _hasMealPlan.value = hasMealPlan
            }
        }
    }

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
                        _hasMealPlan.value = true
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
    
    fun deleteMealPlan(id: Long) {
        viewModelScope.launch {
            mealPlanRepository.deleteMealPlan(id)
        }
    }
    
    fun deleteAllMealPlans() {
        viewModelScope.launch {
            mealPlanRepository.deleteAllMealPlans()
            _uiState.update { it.copy(mealPlan = null) }
            _hasMealPlan.value = false
        }
    }
}

data class ChatUiState(
    val isLoading: Boolean = false,
    val mealPlan: MealPlan? = null,
    val error: String? = null
)
