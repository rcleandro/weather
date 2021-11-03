package br.com.leandro.weather.di

import br.com.leandro.weather.api.WeatherRestApiTask
import br.com.leandro.weather.dao.CityDao
import br.com.leandro.weather.data.WeatherResponse
import br.com.leandro.weather.database.CityDatabase
import br.com.leandro.weather.repository.AddCityRepository
import br.com.leandro.weather.repository.WeatherRepository
import br.com.leandro.weather.ui.fragment.AddCityFragment
import br.com.leandro.weather.ui.fragment.DetailsFragment
import br.com.leandro.weather.ui.fragment.MainFragment
import br.com.leandro.weather.ui.recyclerview.adapter.AddCityAdapter
import br.com.leandro.weather.ui.recyclerview.adapter.WeatherAdapter
import br.com.leandro.weather.ui.viewmodel.AddCityViewModel
import br.com.leandro.weather.ui.viewmodel.DetailsViewModel
import br.com.leandro.weather.ui.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<MainViewModel> { MainViewModel(get()) }
    viewModel<AddCityViewModel> { AddCityViewModel(get(), get()) }
    viewModel<DetailsViewModel> { (weather: WeatherResponse) -> DetailsViewModel(weather) }
}

val uiModule = module {
    factory<MainFragment> { MainFragment() }
    factory<AddCityFragment> { AddCityFragment() }
    factory<DetailsFragment> { DetailsFragment() }
    factory<WeatherAdapter> { WeatherAdapter(get()) }
    factory<AddCityAdapter> { AddCityAdapter(get()) }
}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepository(get()) }
    single<AddCityRepository> { AddCityRepository(get()) }
}

val daoModule = module {
    single<CityDao> { CityDatabase.getInstance(androidContext()).cityDao }
}

val apiModule = module {
    single<WeatherRestApiTask> { WeatherRestApiTask() }
}
