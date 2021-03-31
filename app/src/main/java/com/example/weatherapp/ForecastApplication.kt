package com.example.weatherapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.weatherapp.di.applicationModules
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ForecastApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        startKoin{
            androidContext(this@ForecastApplication)
            modules(applicationModules)
        }
    }
}