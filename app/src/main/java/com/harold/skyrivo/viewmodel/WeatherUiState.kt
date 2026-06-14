package com.harold.skyrivo.viewmodel

import com.harold.skyrivo.network.WeatherResponse

data class WeatherUiState(
    val cityInput: String = "",
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val error: String? = null,
    val permissionAsked: Boolean = false
)
