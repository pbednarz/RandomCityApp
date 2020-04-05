package pl.pbednarz.randomcityapp

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.pbednarz.randomcityapp.di.appModule
import timber.log.Timber

class RandomCityApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@RandomCityApp)
            modules(appModule)
        }
        Stetho.initializeWithDefaults(this)

        startUp()
    }

    private fun startUp() {
        val citiesProducerService: CitiesProducerService = get()
        ProcessLifecycleOwner.get().lifecycle.addObserver(citiesProducerService)
    }
}