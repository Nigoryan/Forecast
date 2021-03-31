package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.CurrentWeatherDao
import com.example.weatherapp.data.db.WeatherLocationDao
import com.example.weatherapp.data.db.entity.WeatherLocation
import com.example.weatherapp.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherapp.data.network.WeatherNetworkDataSource
import com.example.weatherapp.data.network.response.WeatherbitCurrentResponse
import com.example.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: WeatherbitCurrentResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upset(fetchedWeather.data)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value
        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationCity(),locationProvider.getPreferredLocationCountry())
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}