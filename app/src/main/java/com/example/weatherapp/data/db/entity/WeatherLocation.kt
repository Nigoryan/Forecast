package com.example.weatherapp.data.db.entity

import androidx.room.ColumnInfo
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

data class WeatherLocation(
        @ColumnInfo(name = "cityName")
        val cityName: String,
        @ColumnInfo(name = "lat")
        val lat: Double,
        @ColumnInfo(name = "lon")
        val lon: Double,
        @ColumnInfo(name = "timezone")
        val timezone: String,
        @ColumnInfo(name = "ts")
        val ts: Long
){
    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(ts)
            val zoneId = ZoneId.of(timezone)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}
