package com.harold.skyrivo.util

import com.harold.skyrivo.network.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherFormatter {
    fun temperature(value: Double): String = String.format(Locale.US, "%.0f°F", value)

    fun speed(value: Double): String = String.format(Locale.US, "%.0f mph", value)

    fun displayLocation(weather: WeatherResponse): String {
        return listOf(weather.name, weather.sys.country).filter { it.isNotBlank() }.joinToString(", ")
    }

    fun iconUrl(icon: String): String {
        return "https://openweathermap.org/img/wn/${icon}@4x.png"
    }

    fun updatedTime(timestampSeconds: Long): String {
        if (timestampSeconds <= 0L) return "--"
        return SimpleDateFormat("MMM d, h:mm a", Locale.US).format(Date(timestampSeconds * 1000L))
    }
}
