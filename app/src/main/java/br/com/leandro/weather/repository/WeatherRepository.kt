package br.com.leandro.weather.repository

import android.util.Log
import br.com.leandro.weather.api.WeatherRestApiTask
import br.com.leandro.weather.dao.CityDao
import br.com.leandro.weather.data.City
import br.com.leandro.weather.data.WeatherResponse

class WeatherRepository(
    private val cityDao: CityDao
) {

    companion object {
        const val TAG = "WeatherRepository"
    }

    private val listCity: List<City>
        get() = cityDao.getAll()

    private val weatherList = arrayListOf<WeatherResponse>()

    fun getAllWeather(): List<WeatherResponse> {
        listCity.let {
            for (item in listCity) {
                val request = WeatherRestApiTask.retrofitApi().getWeather(item.name)?.execute()
                if (request != null) {
                    if (request.isSuccessful) {
                        request.body()?.let {
                            weatherList.add(it)
                        }
                    } else {
                        request.errorBody()?.let {
                            Log.d(TAG, it.toString())
                        }
                    }
                }
            }
        }
        return weatherList
    }
}