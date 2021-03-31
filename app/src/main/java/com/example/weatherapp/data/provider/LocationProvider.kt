package com.example.weatherapp.data.provider

import com.example.weatherapp.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationCity(): String
    suspend fun getPreferredLocationCountry(): String
}