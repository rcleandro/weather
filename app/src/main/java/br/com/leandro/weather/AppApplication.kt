package br.com.leandro.weather

import android.app.Application
import br.com.leandro.weather.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    daoModule,
                    uiModule,
                    apiModule
                )
            )
        }
    }
}