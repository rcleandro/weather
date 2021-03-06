package br.com.leandro.weather.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.databinding.ItemListBinding
import br.com.leandro.weather.util.Helper

class AddCityAdapter(private var weatherList: List<WeatherResponse>) :
    RecyclerView.Adapter<AddCityAdapter.Holder>() {

    lateinit var onItemClickListener: (item: WeatherResponse) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    }

    override fun getItemCount(): Int = weatherList.count()

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(weatherList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredCity: ArrayList<WeatherResponse>) {
        this.weatherList = filteredCity
        notifyDataSetChanged()
    }

    class Holder(
        private val binding: ItemListBinding,
        private val onItemClickListener: ((item: WeatherResponse) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var weather: WeatherResponse

        @SuppressLint("SetTextI18n")
        fun bind(weather: WeatherResponse) {
            this.weather = weather

            binding.root.setOnClickListener { onItemClickListener?.invoke(weather) }
            binding.text.text = "${weather.name} - ${weather.main.temp.toInt()}ºC"

            Helper.weatherImage(weather, binding.image)
        }
    }
}