package pl.pbednarz.randomcityapp.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import pl.pbednarz.randomcityapp.CitiesProducerService
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.livedata.LiveEvent
import timber.log.Timber

class SplashViewModel(citiesProducerService: CitiesProducerService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _newCityEvent = LiveEvent<City>()

    val newCityLiveData: LiveData<City> = _newCityEvent

    init {
        citiesProducerService.newCityEvent()
            .firstOrError()
            .subscribeBy(
                onSuccess = {
                    _newCityEvent.postValue(it)
                },
                onError = {
                    Timber.e(it, "Error while observing for new cite entry")
                }
            )
            .autoDispose()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }
}