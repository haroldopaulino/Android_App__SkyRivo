package com.harold.skyrivo

import com.harold.skyrivo.data.PreferencesStore
import com.harold.skyrivo.data.WeatherRepository
import com.harold.skyrivo.location.LocationProvider
import com.harold.skyrivo.network.OpenWeatherApi
import com.harold.skyrivo.network.Sys
import com.harold.skyrivo.network.WeatherResponse
import com.harold.skyrivo.viewmodel.WeatherViewModel
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var api: ViewModelFakeApi
    private lateinit var preferences: FakePreferencesStore
    private lateinit var locationProvider: FakeLocationProvider
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        api = ViewModelFakeApi()
        preferences = FakePreferencesStore()
        locationProvider = FakeLocationProvider()
        viewModel = WeatherViewModel(WeatherRepository(api, "key"), preferences, locationProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateCityInputUpdatesUiState() {
        viewModel.updateCityInput("Provo")
        assertEquals("Provo", viewModel.uiState.value.cityInput)
    }

    @Test
    fun blankSearchShowsValidationError() {
        viewModel.updateCityInput("   ")
        viewModel.searchCurrentInput()
        assertEquals("Enter a US city.", viewModel.uiState.value.error)
    }

    @Test
    fun searchCityLoadsWeatherAndSavesCity() = runTest(dispatcher) {
        viewModel.searchCity("Logan")
        advanceUntilIdle()
        assertEquals("Logan", viewModel.uiState.value.weather?.name)
        assertEquals("Logan", preferences.savedCity)
    }

    @Test
    fun loadLastCitySearchesSavedCity() = runTest(dispatcher) {
        preferences.savedCity = "Bountiful"
        preferences.lastCityFlow.value = "Bountiful"
        viewModel.loadLastCity()
        advanceUntilIdle()
        assertEquals("Bountiful", viewModel.uiState.value.weather?.name)
    }

    @Test
    fun loadByCurrentLocationUsesCoordinates() = runTest(dispatcher) {
        locationProvider.coordinates = 40.7 to -111.9
        viewModel.loadByCurrentLocation {}
        advanceUntilIdle()
        assertEquals("Current", viewModel.uiState.value.weather?.name)
        assertNotNull(api.lastLatitude)
    }
}

private class FakePreferencesStore : PreferencesStore {
    val lastCityFlow = MutableStateFlow<String?>(null)
    var savedCity: String? = null
    override val lastCity: Flow<String?> = lastCityFlow

    override suspend fun saveLastCity(city: String) {
        savedCity = city
        lastCityFlow.value = city
    }
}

private class FakeLocationProvider : LocationProvider {
    var coordinates: Pair<Double, Double>? = null
    override suspend fun currentLocation(): Pair<Double, Double>? = coordinates
}

private class ViewModelFakeApi : OpenWeatherApi {
    var lastLatitude: Double? = null

    override suspend fun weatherByCity(city: String, apiKey: String, units: String): WeatherResponse {
        return WeatherResponse(name = city, sys = Sys(country = "US"))
    }

    override suspend fun weatherByCoordinates(latitude: Double, longitude: Double, apiKey: String, units: String): WeatherResponse {
        lastLatitude = latitude
        return WeatherResponse(name = "Current", sys = Sys(country = "US"))
    }

    override fun weatherByCityRx(city: String, apiKey: String, units: String): Single<WeatherResponse> {
        return Single.just(WeatherResponse(name = city, sys = Sys(country = "US")))
    }

    override fun weatherByCoordinatesRx(latitude: Double, longitude: Double, apiKey: String, units: String): Single<WeatherResponse> {
        return Single.just(WeatherResponse(name = "Current", sys = Sys(country = "US")))
    }
}
