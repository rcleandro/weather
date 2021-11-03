package br.com.leandro.weather.util

import android.widget.ImageView
import br.com.leandro.weather.R
import br.com.leandro.weather.data.WeatherResponse

class Helper {
    companion object {
        fun weatherImage(weather: WeatherResponse, image: ImageView) {
            when (weather.weather.first().description) {
                "céu limpo" -> {
                    image.setImageResource(R.drawable.ic_sunny)
                }
                "nublado" -> {
                    image.setImageResource(R.drawable.ic_cloudy)
                }
                "chuva leve" -> {
                    image.setImageResource(R.drawable.ic_medium_rain)
                }
                "chuva moderada" -> {
                    image.setImageResource(R.drawable.ic_heavy_rain)
                }
                "garoa de leve intensidade" -> {
                    image.setImageResource(R.drawable.ic_medium_rain)
                }
                "chuviscos com intensidade de raios" -> {
                    image.setImageResource(R.drawable.ic_lightning)
                }
                "nuvens dispersas" -> {
                    image.setImageResource(R.drawable.ic_sun_and_cloudy)
                }
                "algumas nuvens" -> {
                    image.setImageResource(R.drawable.ic_sun_and_cloudy)
                }
            }
        }
    }
}