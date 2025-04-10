# Meal Planner

A modern Android application that helps users plan their weekly meals and manage shopping lists using AI-generated meal plans.

## Features

### AI-Powered Meal Planning
- Generate personalized weekly meal plans based on user preferences
- Powered by OpenAI API for intelligent meal suggestions
- Detailed recipes and ingredient lists for each meal

### Meal Plan Management
- View your weekly meal plan organized by days
- Access detailed information for each meal including ingredients and recipe steps
- Create new meal plans at any time

### Shopping List
- Automatically generate a shopping list from your meal plan
- Mark items as purchased to track your shopping progress
- Shopping list state persists between app sessions
- Delete individual items or clear the entire list

### Persistence
- All meal plans and shopping lists are stored locally using Room database
- App remembers your last meal plan and shows it immediately on startup

## Technical Details

### Architecture
The application follows the MVVM (Model-View-ViewModel) architecture pattern with clean architecture principles:

- **UI Layer**: Jetpack Compose UI components and ViewModels
- **Domain Layer**: Use cases, repositories interfaces, and domain models
- **Data Layer**: Repository implementations, data sources, and database

### Technologies Used
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit for building native UI
- **Hilt**: Dependency injection
- **Room**: Local database for persistent storage
- **Coroutines & Flow**: Asynchronous programming
- **Navigation Compose**: In-app navigation
- **OpenAI API**: AI-powered meal plan generation
- **Material 3**: Modern design system

### API Integration
The app integrates with the OpenAI API to generate meal plans. It requires an API key which should be stored in the `local.properties` file:

```
openai.api.key=your_api_key_here
```