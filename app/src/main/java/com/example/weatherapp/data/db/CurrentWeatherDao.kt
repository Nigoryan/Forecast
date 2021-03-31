package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherapp.data.db.entity.Data
import com.example.weatherapp.data.db.unitlocalized.current.CurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upset(data: List<Data>)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeather():LiveData<CurrentWeatherEntry>
}