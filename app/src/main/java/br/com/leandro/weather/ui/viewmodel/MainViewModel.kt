package br.com.leandro.weather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.leandro.weather.dao.CityDao
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val cityDao: CityDao
) : ViewModel() {

    companion object {
        const val TAG = "ListViewModel"
    }

    init {
        getAllWeather()
    }

    private var _weatherList = MutableLiveData<List<WeatherResponse>>()
    val weatherList: LiveData<List<WeatherResponse>>
        get() = _weatherList

    private var _lastUpdate = MutableLiveData<String>()
    val lastUpdate: LiveData<String>
        get() = _lastUpdate

    fun update() {
        getAllWeather()
        lastUpdate()
    }

    private fun getAllWeather() {
        Thread {
            try {
                _weatherList.postValue(WeatherRepository(cityDao).getAllWeather())
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }.start()
    }


    fun lastUpdate() {
        val date: Date = GregorianCalendar.getInstance().time
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date.time)
        val formattedHour = SimpleDateFormat("HH:mm:ss", Locale.US).format(date.time)
        _lastUpdate.postValue("Última atualização: $formattedDate - $formattedHour")
    }
}