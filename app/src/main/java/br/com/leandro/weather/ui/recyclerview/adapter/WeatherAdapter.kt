package br.com.leandro.weather.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.databinding.ItemListBinding
import br.com.leandro.weather.util.Helper

class WeatherAdapter(private var weatherList: List<WeatherResponse>) :
    RecyclerView.Adapter<WeatherAdapter.Holder>() {

    lateinit var onItemClickListener: (item: WeatherResponse) -> Unit
    lateinit var onItemLongClickListener: (item: WeatherResponse) -> Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener, onItemLongClickListener
        )
    }

    override fun getItemCount(): Int = weatherList.count()

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(weatherList[position])
    }

    class Holder(
        private val binding: ItemListBinding,
        private val onItemClickListener: ((item: WeatherResponse) -> Unit)?,
        private val onItemLongClickListener: ((item: WeatherResponse) -> Boolean)?
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var weather: WeatherResponse

        @SuppressLint("SetTextI18n")
        fun bind(weather: WeatherResponse) {
            this.weather = weather

            binding.root.setOnClickListener { onItemClickListener?.invoke(weather) }

            if (onItemLongClickListener != null) {
                binding.root.setOnLongClickListener {
                    onItemLongClickListener.invoke(weather)
                }
            }
            binding.text.text = "${weather.name} - ${weather.main.temp.toInt()}ºC"

            Helper.weatherImage(weather, binding.image)
        }
    }
}