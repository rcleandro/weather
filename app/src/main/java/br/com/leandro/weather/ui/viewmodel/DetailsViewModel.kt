package br.com.leandro.weather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.leandro.weather.data.WeatherResponse

class DetailsViewModel(
    private val weather: WeatherResponse,
) : ViewModel() {

    companion object {
        const val TAG = "DetailsViewModel"
    }

    init {
        getWeather()
    }

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse>
        get() = _weatherData

    private fun getWeather() {
        Thread {
            try {
                _weatherData.postValue(weather)
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }.start()
    }
}