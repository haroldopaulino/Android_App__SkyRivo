package com.harold.skyrivo.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String = "",
    val weather: List<WeatherCondition> = emptyList(),
    val main: MainWeather = MainWeather(),
    val wind: Wind = Wind(),
    val sys: Sys = Sys(),
    val dt: Long = 0L
)

@Serializable
data class WeatherCondition(
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)

@Serializable
data class MainWeather(
    val temp: Double = 0.0,
    @SerialName("feels_like") val feelsLike: Double = 0.0,
    @SerialName("temp_min") val tempMin: Double = 0.0,
    @SerialName("temp_max") val tempMax: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0
)

@Serializable
data class Wind(
    val speed: Double = 0.0,
    val deg: Int = 0
)

@Serializable
data class Sys(
    val country: String = ""
)
