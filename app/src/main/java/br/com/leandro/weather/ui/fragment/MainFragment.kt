package br.com.leandro.weather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.leandro.weather.R
import br.com.leandro.weather.data.City
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.databinding.MainFragmentBinding
import br.com.leandro.weather.ui.recyclerview.adapter.WeatherAdapter
import br.com.leandro.weather.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil
            .inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        initListener()
        initObserver()
        refreshVisibility(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(binding.lastUpdate.windowToken, 0)

        viewModel.update()
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.update()
            refreshVisibility(true)
        }

        binding.addButton.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToAddCityFragment()
            findNavController().navigate(direction)
        }

        binding.addLayout.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToAddCityFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initObserver() {
        viewModel.weatherList.observe(viewLifecycleOwner) {
            it.let {
                populateList(it)
                viewModel.lastUpdate()
                refreshVisibility(false)
            }
        }

        viewModel.lastUpdate.observe(viewLifecycleOwner) {
            binding.lastUpdate.text = viewModel.lastUpdate.value
        }
    }

    private fun populateList(list: List<WeatherResponse>) {
        adapter = WeatherAdapter(list)
        adapter.onItemClickListener = { item ->
            val direction = MainFragmentDirections.actionMainFragmentToDetailsFragment(item)
            findNavController().navigate(direction)
        }
        adapter.onItemLongClickListener = { item ->
            Toast.makeText(
                context,
                "${item.name} foi removido da lista de cidades",
                Toast.LENGTH_LONG
            ).show()
            viewModel.removeWeather(City(item.id, item.name))
            viewModel.update()
            refreshVisibility(true)
            true
        }
        setStateList(list)
        binding.weatherListRecycler.adapter = adapter
    }

    private fun refreshVisibility(isRefreshing: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefreshing
    }

    private fun setStateList(list: List<WeatherResponse>) {
        if (list.isEmpty()) {
            binding.lastUpdate.visibility = View.GONE
            binding.swipeRefresh.visibility = View.GONE
            binding.addLayout.visibility = View.VISIBLE
        } else {
            binding.lastUpdate.visibility = View.VISIBLE
            binding.swipeRefresh.visibility = View.VISIBLE
            binding.addLayout.visibility = View.GONE
        }
    }
}
