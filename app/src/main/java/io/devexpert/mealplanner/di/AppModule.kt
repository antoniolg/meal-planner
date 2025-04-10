package io.devexpert.mealplanner.di

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.devexpert.mealplanner.data.datasource.MealPlanDataSource
import io.devexpert.mealplanner.data.datasource.OpenAIMealPlanDataSource
import io.devexpert.mealplanner.data.repository.MealPlanRepositoryImpl
import io.devexpert.mealplanner.domain.repository.MealPlanRepository
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOpenAI(): OpenAI {
        return OpenAI(
            token = "API_KEY",
            timeout = Timeout(socket = 120.seconds)
        )
    }

    @Provides
    @Singleton
    fun provideMealPlanDataSource(openAI: OpenAI, gson: Gson): MealPlanDataSource {
        return OpenAIMealPlanDataSource(openAI, gson)
    }

    @Provides
    @Singleton
    fun provideMealPlanRepository(dataSource: MealPlanDataSource): MealPlanRepository {
        return MealPlanRepositoryImpl(dataSource)
    }
}
