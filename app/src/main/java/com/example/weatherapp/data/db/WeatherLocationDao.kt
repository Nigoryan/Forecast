package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.weatherapp.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherapp.data.db.entity.WeatherLocation

@Dao
interface WeatherLocationDao {
    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getLocation():LiveData<WeatherLocation>
}