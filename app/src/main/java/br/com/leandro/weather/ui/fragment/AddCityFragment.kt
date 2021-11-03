package br.com.leandro.weather.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.leandro.weather.R
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.databinding.AddCityFragmentBinding
import br.com.leandro.weather.ui.recyclerview.adapter.AddCityAdapter
import br.com.leandro.weather.ui.viewmodel.AddCityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class AddCityFragment : Fragment() {

    private lateinit var adapter: AddCityAdapter
    private lateinit var binding: AddCityFragmentBinding
    private val viewModel: AddCityViewModel by viewModel()
    private val controller by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil
            .inflate(inflater, R.layout.add_city_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        initListener()
        initObserver()
        refreshVisibility(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.update()
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.update()
            refreshVisibility(true)
        }

        binding.back.setOnClickListener {
            val direction = AddCityFragmentDirections.actionAddCityFragmentToMainFragment()
            controller.navigate(direction)
        }
    }

    private fun initObserver() {
        viewModel.weatherList.observe(viewLifecycleOwner) {
            populateList(it)
            refreshVisibility(false)
        }
    }

    private fun populateList(list: List<WeatherResponse>) {
        adapter = AddCityAdapter(list)

        adapter.onItemClickListener = { item ->
            viewModel.save(item.name)
            Toast.makeText(
                context,
                "${item.name} foi adicionado à lista de cidades",
                Toast.LENGTH_LONG
            ).show()
            val direction = AddCityFragmentDirections.actionAddCityFragmentToMainFragment()
            findNavController().navigate(direction)
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                filter(text.toString(), list)
            }
        })
        binding.searchCitiesRecycler.adapter = adapter
    }

    private fun filter(text: String, list: List<WeatherResponse>) {
        val filteredCity: ArrayList<WeatherResponse> = ArrayList()
        for (item in list) {
            if (item.name.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            )
                filteredCity.add(item)
        }
        adapter.filterList(filteredCity)
    }

    private fun refreshVisibility(isRefreshing: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefreshing
    }
}
