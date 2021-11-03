package br.com.leandro.weather.data

import java.io.Serializable

data class WeatherResponse(
    val coord: Coord,
    val sys: Sys,
    val weather: ArrayList<Weather>,
    val visibility: Int,
    val main: Main,
    val wind: Wind,
    var rain: Rain,
    var clouds: Clouds,
    var dt: Float,
    var id: Long,
    var name: String,
    val cod: Float
) : Serializable

class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
) : Serializable

class Clouds(
    var all: Float
) : Serializable

class Rain(
    var h3: Float
) : Serializable

class Wind(
    var speed: Float,
    var deg: Float
) : Serializable

class Main(
    var temp: Float,
    var humidity: Float,
    var pressure: Float,
    var temp_min: Float,
    var temp_max: Float
) : Serializable

class Sys(
    var country: String,
    var sunrise: Long,
    var sunset: Long
) : Serializable

class Coord(
    var lon: Float,
    var lat: Float
) : Serializable

