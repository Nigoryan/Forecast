package com.example.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.internal.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather()
    }

    val weatherLocation by lazyDeffered {
        forecastRepository.getWeatherLocation()
    }
}