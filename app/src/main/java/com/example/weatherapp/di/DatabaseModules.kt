package com.example.weatherapp.di

import com.example.weatherapp.data.db.ForecastDatabase
import com.example.weatherapp.data.network.*
import com.example.weatherapp.data.provider.LocationProvider
import com.example.weatherapp.data.provider.LocationProviderImpl
import com.example.weatherapp.data.provider.UnitProvider
import com.example.weatherapp.data.provider.UnitProviderImpl
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.data.repository.ForecastRepositoryImpl
import com.example.weatherapp.ui.weather.current.CurrentWeatherViewModel
import com.example.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val databaseModule = module{
    single { ForecastDatabase(get()) }
    single { get<ForecastDatabase>().currentWeatherDao() }
    single { get<ForecastDatabase>().weatherLocationDao() }
    single<ConnectivityInterceptor> { ConnectivityInterceptorImpl(get()) }
    single { WeatherbitApiService(get()) }
    
    single<WeatherNetworkDataSource> { WeatherNetworkDataSourceImpl(get()) }
    single<LocationProvider> { LocationProviderImpl() }
    single<ForecastRepository> { ForecastRepositoryImpl(get(),get(),get(),get()) }
    factory { CurrentWeatherViewModelFactory(get()) }
    single<UnitProvider> { UnitProviderImpl(get()) }
    viewModel { CurrentWeatherViewModel(get()) }

}