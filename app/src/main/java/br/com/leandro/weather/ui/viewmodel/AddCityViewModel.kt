package br.com.leandro.weather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandro.weather.dao.CityDao
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.repository.AddCityRepository
import kotlinx.coroutines.launch

class AddCityViewModel(
    private val cityRepository: AddCityRepository,
    private val cityDao: CityDao
) : ViewModel() {

    init {
        getAllWeather()
    }

    fun update() {
        getAllWeather()
    }

    private var _weatherList = MutableLiveData<List<WeatherResponse>>()
    val weatherList: LiveData<List<WeatherResponse>>
        get() = _weatherList

    private fun getAllWeather() {
        Thread {
            try {
                _weatherList.postValue(AddCityRepository(cityDao).getAllWeather())
            } catch (exception: Exception) {
                Log.d(MainViewModel.TAG, exception.message.toString())
            }
        }.start()
    }

    fun save(newCity: String) {
        viewModelScope.launch {
            cityRepository.save(newCity)
        }
    }
}
