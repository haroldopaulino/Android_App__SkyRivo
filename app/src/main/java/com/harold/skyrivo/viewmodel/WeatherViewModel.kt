package com.harold.skyrivo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harold.skyrivo.data.PreferencesStore
import com.harold.skyrivo.data.WeatherRepository
import com.harold.skyrivo.location.LocationProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val preferencesStore: PreferencesStore,
    private val locationProvider: LocationProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun updateCityInput(value: String) {
        _uiState.update { it.copy(cityInput = value) }
    }

    fun markPermissionAsked() {
        _uiState.update { it.copy(permissionAsked = true) }
    }

    fun loadLastCity(onMissingCity: () -> Unit = {}) {
        viewModelScope.launch {
            val city = preferencesStore.lastCity.first()?.trim().orEmpty()
            if (city.isNotBlank()) {
                updateCityInput(city)
                searchCity(city)
            } else {
                onMissingCity()
            }
        }
    }

    fun searchCurrentInput() {
        val city = uiState.value.cityInput.trim()
        if (city.isBlank()) {
            _uiState.update { it.copy(error = "Enter a US city.") }
            return
        }
        searchCity(city)
    }

    fun searchCity(city: String) {
        val cleanCity = city.trim()
        if (cleanCity.isBlank()) return
        viewModelScope.launch {
            preferencesStore.saveLastCity(cleanCity)
            _uiState.update { it.copy(isLoading = true, error = null, cityInput = cleanCity) }
            runCatching { repository.byCity(cleanCity) }
                .onSuccess { weather ->
                    _uiState.update { it.copy(isLoading = false, weather = weather, cityInput = cleanCity) }
                }
                .onFailure { throwable ->
                    _uiState.update { it.copy(isLoading = false, error = throwable.message ?: "Unable to load weather.") }
                }
        }
    }

    fun loadByCurrentLocation(onMissingLocation: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val coordinates = runCatching { locationProvider.currentLocation() }.getOrNull()
            if (coordinates == null) {
                _uiState.update { it.copy(isLoading = false, error = "Current location is unavailable.") }
                onMissingLocation()
                return@launch
            }
            runCatching { repository.byCoordinates(coordinates.first, coordinates.second) }
                .onSuccess { weather ->
                    val city = weather.name.ifBlank { uiState.value.cityInput }
                    if (city.isNotBlank()) preferencesStore.saveLastCity(city)
                    _uiState.update { it.copy(isLoading = false, weather = weather, cityInput = city) }
                }
                .onFailure { throwable ->
                    _uiState.update { it.copy(isLoading = false, error = throwable.message ?: "Unable to load weather.") }
                }
        }
    }
}
