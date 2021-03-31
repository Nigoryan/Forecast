package com.example.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.network.response.WeatherbitCurrentResponse
import com.example.weatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherbitApiService: WeatherbitApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<WeatherbitCurrentResponse>()
    override val downloadedCurrentWeather: LiveData<WeatherbitCurrentResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(city: String, country: String) {
        try{
            val fetchedCurrentWeather = weatherbitApiService
                .getCurrentWeather(city, country)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e:NoConnectivityException){
            Log.e("Connectivity","No internet connection",e)
        }
    }
}