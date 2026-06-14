# SkyRivo Android Weather App

**Modern Android weather application built with Kotlin, Jetpack Compose, Material 3, MVVM-style state management, Retrofit/OkHttp, Kotlin Serialization, DataStore preferences, Koin dependency injection, Coil image loading, and OpenWeather API integration.**

SkyRivo is a native Android weather app that lets users search weather by city or load weather from the device's current location. The project demonstrates modern Android app development with Compose UI, structured data/network layers, dependency injection, persisted preferences, runtime location permissions, and REST API integration.

The app is a strong portfolio project because it shows practical mobile product engineering: clean package organization, real API consumption, reactive UI state, saved user preferences, location-aware behavior, and modern Android build tooling.

---

## Why This Project Matters

SkyRivo is more than a simple weather screen. It demonstrates the kind of Android engineering used in production mobile apps:

- Kotlin Android development
- Jetpack Compose UI
- Material 3 design
- MVVM-style ViewModel state handling
- Retrofit REST API integration
- OkHttp logging support
- Kotlin Serialization models
- Koin dependency injection
- DataStore preferences
- Google Play Services location support
- Runtime location permission handling
- Coil image loading
- Navigation Compose
- Coroutines and suspend API calls
- RxJava support for alternate network call style
- Modern Gradle Kotlin DSL setup

---

## Project Overview

SkyRivo provides weather lookup through two main workflows:

1. **City search**
   - The user enters a city name.
   - The app calls the OpenWeather current weather API.
   - The UI displays the returned weather conditions.

2. **Current location weather**
   - The app requests location permissions.
   - The app reads the device's current location.
   - The app calls the OpenWeather API using latitude and longitude.
   - The UI displays weather for the current area.

The app also persists the latest city or preference data so the user can return to a previous weather lookup experience.

---

## Main Features

### Weather by City

The app supports city-based weather lookup using OpenWeather's current weather endpoint.

The network API includes a city query method that sends:

- City name
- API key
- Unit preference

### Weather by Coordinates

The app supports coordinate-based weather lookup using latitude and longitude.

This enables current-location weather and demonstrates location-aware mobile app development.

### Runtime Location Permissions

The Android manifest declares coarse and fine location permissions, and the app is structured to request permission before using current location.

### Modern Compose UI

The UI is built with Jetpack Compose and Material 3. The project includes a dedicated `WeatherScreen.kt` and `Theme.kt`, keeping UI composition separate from data and network logic.

### MVVM-Style State Management

The app includes a `WeatherViewModel` and `WeatherUiState` model. This separates UI rendering from business logic and async data loading.

### Retrofit + Kotlin Serialization

The app integrates with OpenWeather using Retrofit and Kotlin Serialization. This provides typed API models, structured JSON parsing, and clean API definitions.

### Koin Dependency Injection

The project includes a `di` package and Koin dependencies. This helps keep networking, repository, and ViewModel construction organized and testable.

### DataStore Preferences

The app uses AndroidX DataStore preferences through `PreferencesStore.kt` to persist lightweight user/app settings.

### Location Layer

The project includes a `location` package and Google Play Services location dependency, providing a dedicated place for device location behavior.

### Coil Image Loading

The app includes Coil Compose support, which can be used for loading weather icons or other remote weather-related visual assets.

---

## Technical Stack

| Area | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Design system | Material 3 |
| Architecture | MVVM-style ViewModel + repository |
| Networking | Retrofit, OkHttp |
| JSON | Kotlin Serialization |
| Dependency injection | Koin |
| Preferences | AndroidX DataStore |
| Location | Google Play Services Location |
| Image loading | Coil Compose |
| Navigation | Navigation Compose |
| Async | Kotlin Coroutines |
| Alternate async/network support | RxJava / Retrofit RxJava adapter |
| Build system | Gradle Kotlin DSL |
| Java toolchain | 17 |
| Compile SDK | 37 |
| Target SDK | 37 |
| Minimum SDK | 26 |
| License | GPL-3.0 |

---

## Repository Structure

```text
Android_App__SkyRivo/
├── app/
│   ├── src/main/
│   │   ├── java/com/harold/skyrivo/
│   │   │   ├── data/
│   │   │   │   ├── PreferencesStore.kt
│   │   │   │   └── WeatherRepository.kt
│   │   │   ├── di/
│   │   │   ├── location/
│   │   │   ├── navigation/
│   │   │   ├── network/
│   │   │   │   ├── NetworkModule.kt
│   │   │   │   ├── OpenWeatherApi.kt
│   │   │   │   └── WeatherModels.kt
│   │   │   ├── ui/
│   │   │   │   ├── Theme.kt
│   │   │   │   └── WeatherScreen.kt
│   │   │   ├── util/
│   │   │   ├── viewmodel/
│   │   │   │   ├── WeatherUiState.kt
│   │   │   │   └── WeatherViewModel.kt
│   │   │   ├── MainActivity.kt
│   │   │   └── SkyRivoApp.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── README.md
└── LICENSE
```

---

## Architecture

SkyRivo uses a layered app structure:

```text
UI layer
  └── WeatherScreen.kt
      └── WeatherViewModel.kt
          └── WeatherRepository.kt
              └── OpenWeatherApi.kt
```

Supporting layers include:

- `di` for dependency injection
- `network` for API interfaces and weather models
- `data` for repository and preferences
- `location` for current-location behavior
- `navigation` for Compose navigation
- `ui` for Compose screens and theme
- `viewmodel` for UI state and business logic

This structure keeps UI, state, data access, networking, and platform services separated.

---

## Key Source Areas

### `OpenWeatherApi.kt`

Defines Retrofit API calls for current weather by city and by coordinates.

The API supports both coroutine-based `suspend` functions and RxJava `Single` variants, showing familiarity with multiple Android async patterns.

### `WeatherRepository.kt`

Acts as the data layer between the ViewModel and weather API. This keeps the UI state layer from depending directly on Retrofit.

### `WeatherViewModel.kt`

Owns weather loading behavior, user interaction handling, UI state updates, and error/loading state transitions.

### `WeatherUiState.kt`

Defines the state shape used by the Compose UI, helping keep the screen reactive and predictable.

### `WeatherScreen.kt`

Provides the main weather interface using Jetpack Compose and Material 3.

### `PreferencesStore.kt`

Persists lightweight app data using DataStore preferences, such as user-entered city or weather-related settings.

### `SkyRivoApp.kt`

Defines the application class and initializes app-wide dependencies.

### `AndroidManifest.xml`

Declares internet access and location permissions, sets the application class, app label, launcher icon, and main activity.

---

## Data Flow

```text
User searches city or requests current location
      ↓
WeatherViewModel updates loading state
      ↓
WeatherRepository requests weather data
      ↓
OpenWeatherApi calls OpenWeather endpoint
      ↓
WeatherModels decode response
      ↓
WeatherUiState updates
      ↓
WeatherScreen recomposes with new weather data
      ↓
PreferencesStore can persist latest city/settings
```

This flow demonstrates a standard production Android pattern: user intent, ViewModel state, repository abstraction, network API, typed model parsing, and reactive UI rendering.

---

## Weather API Support

The app integrates with OpenWeather's current weather endpoint:

- `data/2.5/weather?q={city}`
- `data/2.5/weather?lat={latitude}&lon={longitude}`

The app uses imperial units by default.

For production use, the API key should be moved out of source control and supplied through a secure build-time or runtime configuration system.

---

## Android Permissions

SkyRivo declares:

- `android.permission.INTERNET`
- `android.permission.ACCESS_COARSE_LOCATION`
- `android.permission.ACCESS_FINE_LOCATION`

These permissions support network-based weather lookup and current-location weather loading.

---

## Skills Demonstrated

This repository demonstrates several Android engineering skills:

- Kotlin Android development
- Jetpack Compose UI development
- Material 3 app design
- ViewModel-based state management
- Repository pattern
- REST API integration
- Retrofit API definitions
- OkHttp configuration
- Kotlin Serialization model parsing
- DataStore preferences
- Google Play Services location APIs
- Runtime permission-aware app behavior
- Dependency injection with Koin
- Navigation Compose
- Coil image loading
- Gradle Kotlin DSL configuration
- Modern Android SDK targeting
- Java/Kotlin toolchain configuration

---

## Product Design Notes

### Weather as a Real API Workflow

Weather apps are useful portfolio projects when they are built around real networking, error handling, location permissions, persisted user preferences, and polished UI state. SkyRivo includes these production-relevant pieces.

### City + Location Entry Points

Supporting both typed city lookup and current-location lookup makes the app more realistic and user-friendly.

### Persisted User Experience

Persisting the latest city or preference data improves the user experience because the app can restore useful context when reopened.

### Clean Android Package Organization

The package structure makes it easy to identify responsibilities and extend the app with additional forecast, maps, alerts, widgets, or offline behavior.

---

## How to Build

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle sync using the included wrapper.
4. Configure a valid OpenWeather API key through a secure build configuration approach.
5. Select the `app` target.
6. Run the app on an Android emulator or physical Android device.
7. Grant location permission if testing current-location weather.
8. Search for a city or load weather from current location.

---

## Owner

by Harold Paulino

---

## License

This project is licensed under the GPL-3.0 license.
