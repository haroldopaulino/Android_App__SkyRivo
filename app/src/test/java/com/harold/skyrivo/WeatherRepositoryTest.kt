package com.harold.skyrivo

import com.harold.skyrivo.data.WeatherRepository
import com.harold.skyrivo.network.MainWeather
import com.harold.skyrivo.network.OpenWeatherApi
import com.harold.skyrivo.network.Sys
import com.harold.skyrivo.network.WeatherResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherRepositoryTest {
    private val api = FakeOpenWeatherApi()
    private val repository = WeatherRepository(api, "test-key")

    @Test
    fun byCityTrimsCityBeforeRequest() = runTest {
        repository.byCity("  Ogden  ")
        assertEquals("Ogden", api.lastCity)
    }

    @Test
    fun byCityUsesConfiguredApiKey() = runTest {
        repository.byCity("Layton")
        assertEquals("test-key", api.lastApiKey)
    }

    @Test
    fun byCoordinatesPassesLatitudeAndLongitude() = runTest {
        repository.byCoordinates(41.1, -112.2)
        assertEquals(41.1, api.lastLatitude, 0.0)
        assertEquals(-112.2, api.lastLongitude, 0.0)
    }

    @Test
    fun byCityRxTrimsCityBeforeRequest() {
        repository.byCityRx("  Murray  ").test().assertComplete()
        assertEquals("Murray", api.lastCity)
    }

    @Test
    fun loadCityAndCoordinatesRunsBothRequests() = runTest {
        val result = repository.loadCityAndCoordinates("Clearfield", 41.2, -112.0)
        assertEquals("Clearfield", result.first.name)
        assertEquals("Coordinates", result.second.name)
    }
}

private class FakeOpenWeatherApi : OpenWeatherApi {
    var lastCity: String = ""
    var lastApiKey: String = ""
    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0

    override suspend fun weatherByCity(city: String, apiKey: String, units: String): WeatherResponse {
        lastCity = city
        lastApiKey = apiKey
        return WeatherResponse(name = city, main = MainWeather(temp = 70.0), sys = Sys(country = "US"))
    }

    override suspend fun weatherByCoordinates(latitude: Double, longitude: Double, apiKey: String, units: String): WeatherResponse {
        lastLatitude = latitude
        lastLongitude = longitude
        lastApiKey = apiKey
        return WeatherResponse(name = "Coordinates", main = MainWeather(temp = 71.0), sys = Sys(country = "US"))
    }

    override fun weatherByCityRx(city: String, apiKey: String, units: String): Single<WeatherResponse> {
        lastCity = city
        lastApiKey = apiKey
        return Single.just(WeatherResponse(name = city, sys = Sys(country = "US")))
    }

    override fun weatherByCoordinatesRx(latitude: Double, longitude: Double, apiKey: String, units: String): Single<WeatherResponse> {
        lastLatitude = latitude
        lastLongitude = longitude
        lastApiKey = apiKey
        return Single.just(WeatherResponse(name = "Coordinates", sys = Sys(country = "US")))
    }
}
