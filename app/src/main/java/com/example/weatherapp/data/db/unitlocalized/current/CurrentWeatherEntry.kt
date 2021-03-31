package com.example.weatherapp.data.db.unitlocalized.current

import androidx.room.ColumnInfo

data class CurrentWeatherEntry(
    @ColumnInfo(name = "temp")
    override val temperature: Double,
    @ColumnInfo(name = "weather_description")
    override val conditionText: String,
    @ColumnInfo(name = "weather_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windSpd")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precip")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "appTemp")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "vis")
    override val visibilityDistance: Double
):UnitSpecificCurrentWeatherEntry
