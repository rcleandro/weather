package br.com.leandro.weather.api

import br.com.leandro.weather.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather?APPID=76a35a17f3e1bce771a09f3555b614a8&units=metric&lang=pt_br")
    fun getWeather(@Query("q") name: String?): Call<WeatherResponse?>?
}