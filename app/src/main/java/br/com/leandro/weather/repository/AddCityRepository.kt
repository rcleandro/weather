package br.com.leandro.weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
import br.com.leandro.weather.api.WeatherRestApiTask
import br.com.leandro.weather.dao.CityDao
import br.com.leandro.weather.data.City
import br.com.leandro.weather.data.WeatherResponse

class AddCityRepository(
    private val cityDao: CityDao
) {

    companion object {
        const val TAG = "AddCityRepository"
    }

    private val listOfCitiesMock = mutableListOf<City>(
        City(0L, "São Paulo,BR"),
        City(1L, "Rio de Janeiro,BR"),
        City(2L, "Brasília,BR"),
        City(3L, "Salvador,BR"),
        City(4L, "Fortaleza,BR"),
        City(5L, "Belo Horizonte,BR"),
        City(6L, "Manaus,BR"),
        City(7L, "Curitiba,BR"),
        City(8L, "Recife,BR"),
        City(9L, "Goiânia,BR")
    )

    private val _listCity: LiveData<List<City>> = cityDao.getAll()
    private val listCity: LiveData<List<City>>
        get() = _listCity

    private val weatherList = arrayListOf<WeatherResponse>()

    suspend fun save(newCity: String) {
        cityDao.save(City(name = "$newCity,BR"))
    }

    fun getAllWeather(): List<WeatherResponse> {
        if (listCity.value != null) {
            for (city in listOfCitiesMock) {
                for (item in listCity.value!!) {
                    if (city.name != item.name) {
                        makeRequest(city.name)
                    }
                }
            }
        } else for (city in listOfCitiesMock) makeRequest(city.name)
        return weatherList
    }

    private fun makeRequest(city: String) {
        val request = WeatherRestApiTask.retrofitApi().getWeather(city)?.execute()
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