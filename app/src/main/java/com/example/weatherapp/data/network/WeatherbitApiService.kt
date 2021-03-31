package com.example.weatherapp.data.network

import com.example.weatherapp.data.network.response.WeatherbitCurrentResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


const val  API_KEY = "2172f12f4d4d4b96bb79578a1a028ab8"

interface WeatherbitApiService {
    @GET("current")
    fun getCurrentWeather(
        @Query("city") city:String,
        @Query("country") country:String
    ): Deferred<WeatherbitCurrentResponse>

    companion object {
        private lateinit var service: WeatherbitApiService
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherbitApiService {
            if (!::service.isInitialized){
                val requestInterceptor = Interceptor { chain ->
                    val url = chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                    val  request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    return@Interceptor chain.proceed(request)
                }
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(connectivityInterceptor)
                    .callTimeout(3, TimeUnit.SECONDS)
                    .build()
                service = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.weatherbit.io/v2.0/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherbitApiService::class.java)
            }
            return service
        }
    }
}