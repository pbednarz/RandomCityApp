package pl.pbednarz.randomcityapp.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import pl.pbednarz.randomcityapp.CitiesProducerService
import pl.pbednarz.randomcityapp.di.SchedulersProvider
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.livedata.Event
import timber.log.Timber

class SplashViewModel(
    citiesProducerService: CitiesProducerService,
    schedulersProvider: SchedulersProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _newCityEvent = MutableLiveData<Event<City>>()

    val newCityLiveData: LiveData<Event<City>> = _newCityEvent

    init {
        citiesProducerService.newCityEvent()
            .firstOrError()
            .observeOn(schedulersProvider.ui())
            .subscribeBy(
                onSuccess = {
                    _newCityEvent.value = Event(it)
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