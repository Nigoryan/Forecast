package com.example.weatherapp.ui.weather.current

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.weatherapp.R
import com.example.weatherapp.internal.glide.GlideApp
import com.example.weatherapp.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CurrentWeatherFragment : ScopedFragment() {

    @BindView(R.id.group_loading) lateinit var groupLoading:androidx.constraintlayout.widget.Group
    @BindView(R.id.textView_temperature) lateinit var tv_temperature: TextView
    @BindView(R.id.textView_feels_like_temperature) lateinit var tv_feels_like_temperature: TextView
    @BindView(R.id.textView_condition) lateinit var tv_condition: TextView
    @BindView(R.id.textView_precipitation) lateinit var tv_precipitation: TextView
    @BindView(R.id.textView_wind) lateinit var tv_wind: TextView
    @BindView(R.id.textView_visibility) lateinit var tv_visibility: TextView
    @BindView(R.id.imageView_condition_icon) lateinit var iv_condition: ImageView
    private lateinit var unbinder: Unbinder

    private val viewModel by inject<CurrentWeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_weather_fragment, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it==null) return@Observer
            groupLoading.visibility = View.GONE
            updateDateToToday()
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            val uri = "@drawable/"+it.conditionIconUrl
            val imageResource = resources.getIdentifier(uri,null, context?.packageName)
            val logoDrawable = ResourcesCompat.getDrawable(resources,imageResource,null)
            GlideApp.with(this@CurrentWeatherFragment)
                    .load(logoDrawable)
                    .into(iv_condition)
        })

        weatherLocation.observe(viewLifecycleOwner, Observer { location->
            if(location == null) return@Observer
            updateLocation(location.cityName)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, fellsLike: Double){
        tv_temperature.text = "$temperature"
        tv_feels_like_temperature.text = "Feels like $fellsLike"
    }

    private fun updateCondition(condition: String){
        tv_condition.text = condition
    }

    private fun updatePrecipitation(precipitation: Double){
        tv_precipitation.text = "Precipitation: $precipitation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double){
        tv_wind.text = "Wind: $windDirection, $windSpeed"
    }

    private fun updateVisibility(visibilityDistance: Double){
        tv_visibility.text = "Visibility: $visibilityDistance"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}