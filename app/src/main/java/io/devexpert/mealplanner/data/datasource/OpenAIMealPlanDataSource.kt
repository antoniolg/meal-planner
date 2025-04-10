package io.devexpert.mealplanner.data.datasource

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.google.gson.Gson
import io.devexpert.mealplanner.domain.model.MealPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenAIMealPlanDataSource @Inject constructor(
    private val openAI: OpenAI,
    private val gson: Gson
) : MealPlanDataSource {

    override suspend fun generateMealPlan(prompt: String): Flow<Result<MealPlan>> = flow {
        try {
            val systemPrompt = """
                You are a meal planning assistant. Create a weekly meal plan based on the user's request.
                Return the response in JSON format with the following structure:
                {
                  "days": [
                    {
                      "day": "Monday",
                      "meals": [
                        {
                          "name": "Meal name",
                          "recipe": {
                            "steps": ["Step 1", "Step 2", "..."]
                          },
                          "ingredients": [
                            {"name": "Ingredient name", "quantity": "amount"},
                            {"name": "Ingredient name", "quantity": "amount"}
                          ]
                        }
                      ]
                    }
                  ]
                }
                Include all days of the week from Monday to Sunday.
                Each day should have at least one meal.
                Provide detailed recipe steps and precise ingredient quantities.
            """.trimIndent()

            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-4o-mini"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = systemPrompt
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = prompt
                    )
                )
            )

            val completion = openAI.chatCompletion(chatCompletionRequest)
            val responseContent = completion.choices.first().message.content ?: ""
            
            // Extract JSON from the response (in case there's additional text)
            val jsonRegex = """\{[\s\S]*\}""".toRegex()
            val jsonMatch = jsonRegex.find(responseContent)
            val jsonString = jsonMatch?.value ?: responseContent
            
            val mealPlan = gson.fromJson(jsonString, MealPlan::class.java)
            emit(Result.success(mealPlan))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
