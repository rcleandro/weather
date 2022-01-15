package br.com.leandro.weather.repository

import android.util.Log
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

    private val listOfCitiesMock = mutableListOf(
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

    private val listCity: List<City>
        get() = cityDao.getAll()

    private val weatherList = arrayListOf<WeatherResponse>()

    suspend fun save(newCity: String) {
        cityDao.save(City(name = "$newCity,BR"))
    }

    fun getAllWeather(): List<WeatherResponse> {
        listOfCitiesMock.forEach { city ->
            if (!checkCity(city.name))
                makeRequest(city.name)
        }
        return weatherList
    }

    private fun checkCity(city: String): Boolean {
        listCity.forEach { item ->
            if (item.name == city)
                return true
        }
        return false
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