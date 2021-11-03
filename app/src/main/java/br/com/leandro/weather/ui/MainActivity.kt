package br.com.leandro.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.leandro.weather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.main_activity)
    }
}