package com.example.weatherapp.data.provider

import com.example.weatherapp.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationCity(): String {
        return "Minsk"
    }

    override suspend fun getPreferredLocationCountry(): String {
        return "Belarus"
    }

}