package br.com.leandro.weather.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.leandro.weather.R
import br.com.leandro.weather.databinding.DetailsFragmentBinding
import br.com.leandro.weather.ui.viewmodel.DetailsViewModel
import br.com.leandro.weather.util.Helper
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private val weather by lazy { args.weather }
    private val viewModel: DetailsViewModel by viewModel { parametersOf(weather) }
    private val controller by lazy { findNavController() }
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil
            .inflate(inflater, R.layout.details_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.back.setOnClickListener {
            val direction = DetailsFragmentDirections.actionDetailsFragmentToMainFragment()
            controller.navigate(direction)
        }

        loadWeatherInformation()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun loadWeatherInformation() {
        viewModel.weatherData.observe(viewLifecycleOwner) {
            it?.let { weather ->
                binding.cityName.text = weather.name
                binding.descriptionText.text = weather.weather.first().description
                binding.tempText.text = weather.main.temp.toInt().toString()
                binding.minTempText.text = "Mín: ${weather.main.temp_min.toInt()}ºC"
                binding.maxTempText.text = "Max: ${weather.main.temp_max.toInt()}ºC"
                binding.windSpeedText.text = "${weather.wind.speed} m/seg"
                binding.visibilityText.text = weather.visibility.toString()
                binding.humidityText.text = "${weather.main.humidity.toInt()}%"
                binding.pressureText.text = "${weather.main.pressure.toInt()} hPa"
                Helper.weatherImage(weather, binding.image)
            }
        }
    }
}