package com.harold.skyrivo

import com.harold.skyrivo.network.Sys
import com.harold.skyrivo.network.WeatherResponse
import com.harold.skyrivo.util.WeatherFormatter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherFormatterTest {
    @Test
    fun temperatureRoundsToFahrenheit() {
        assertEquals("72°F", WeatherFormatter.temperature(72.4))
    }

    @Test
    fun speedRoundsToMilesPerHour() {
        assertEquals("8 mph", WeatherFormatter.speed(8.3))
    }

    @Test
    fun displayLocationIncludesCountryWhenPresent() {
        val weather = WeatherResponse(name = "Sandy", sys = Sys(country = "US"))
        assertEquals("Sandy, US", WeatherFormatter.displayLocation(weather))
    }

    @Test
    fun iconUrlUsesOpenWeatherIconPath() {
        assertEquals("https://openweathermap.org/img/wn/10d@4x.png", WeatherFormatter.iconUrl("10d"))
    }

    @Test
    fun updatedTimeReturnsPlaceholderForMissingTimestamp() {
        assertEquals("--", WeatherFormatter.updatedTime(0L))
    }
}
