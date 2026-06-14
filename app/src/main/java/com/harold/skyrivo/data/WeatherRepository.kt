package com.harold.skyrivo.data

import com.harold.skyrivo.network.OpenWeatherApi
import com.harold.skyrivo.network.WeatherResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class WeatherRepository(
    private val api: OpenWeatherApi,
    private val apiKey: String
) {
    suspend fun byCity(city: String): WeatherResponse {
        return api.weatherByCity(city = city.trim(), apiKey = apiKey)
    }

    suspend fun byCoordinates(latitude: Double, longitude: Double): WeatherResponse {
        return api.weatherByCoordinates(latitude = latitude, longitude = longitude, apiKey = apiKey)
    }

    fun byCityRx(city: String): Single<WeatherResponse> {
        return api.weatherByCityRx(city = city.trim(), apiKey = apiKey)
    }

    fun byCoordinatesRx(latitude: Double, longitude: Double): Single<WeatherResponse> {
        return api.weatherByCoordinatesRx(latitude = latitude, longitude = longitude, apiKey = apiKey)
    }

    suspend fun loadCityAndCoordinates(city: String, latitude: Double, longitude: Double): Pair<WeatherResponse, WeatherResponse> {
        return coroutineScope {
            val cityWeather = async { byCity(city) }
            val coordinateWeather = async { byCoordinates(latitude, longitude) }
            cityWeather.await() to coordinateWeather.await()
        }
    }
}
