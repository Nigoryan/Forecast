package com.example.weatherapp.data.network

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.network.response.WeatherbitCurrentResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<WeatherbitCurrentResponse>
    suspend fun fetchCurrentWeather(
        city:String,
        country:String
    )
}