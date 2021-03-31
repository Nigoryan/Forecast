package com.example.weatherapp.data.network.response


import com.example.weatherapp.data.db.entity.Data

data class WeatherbitCurrentResponse(
    val count: Int,
    val data: List<Data>
)