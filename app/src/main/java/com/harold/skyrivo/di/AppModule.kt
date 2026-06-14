package com.harold.skyrivo.di

import com.harold.skyrivo.BuildConfig
import com.harold.skyrivo.data.DataStorePreferencesStore
import com.harold.skyrivo.data.PreferencesStore
import com.harold.skyrivo.data.WeatherRepository
import com.harold.skyrivo.location.AndroidLocationProvider
import com.harold.skyrivo.location.LocationProvider
import com.harold.skyrivo.network.NetworkModule
import com.harold.skyrivo.network.OpenWeatherApi
import com.harold.skyrivo.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<OpenWeatherApi> { NetworkModule.api }
    single(named("openWeatherApiKey")) { BuildConfig.OPEN_WEATHER_API_KEY }
    single<PreferencesStore> { DataStorePreferencesStore(androidContext()) }
    single<LocationProvider> { AndroidLocationProvider(androidContext()) }
    single { WeatherRepository(get(), get(named("openWeatherApiKey"))) }
    viewModel { WeatherViewModel(get(), get(), get()) }
}
