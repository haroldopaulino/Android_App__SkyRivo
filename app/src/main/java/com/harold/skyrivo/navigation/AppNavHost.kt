package com.harold.skyrivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harold.skyrivo.ui.WeatherScreen
import com.harold.skyrivo.viewmodel.WeatherViewModel

object SkyRivoRoutes {
    const val Weather = "weather"
}

@Composable
fun AppNavHost(viewModel: WeatherViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SkyRivoRoutes.Weather) {
        composable(SkyRivoRoutes.Weather) {
            WeatherScreen(viewModel)
        }
    }
}
