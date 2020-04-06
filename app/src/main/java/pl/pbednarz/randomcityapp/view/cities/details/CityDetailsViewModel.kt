package pl.pbednarz.randomcityapp.view.cities.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.pbednarz.randomcityapp.di.SchedulersProvider
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.CityLocation
import pl.pbednarz.randomcityapp.domain.usecases.FetchCityLocationUseCase
import timber.log.Timber

class CityDetailsViewModel(
    private val schedulersProvider: SchedulersProvider,
    private val fetchCityLocationUseCase: FetchCityLocationUseCase
) : ViewModel() {

    private val _selectedCity: MutableLiveData<City> = MutableLiveData()
    private val _cityLocationViewState: MutableLiveData<ViewState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    val selectedCity: LiveData<City> = _selectedCity
    val cityLocationViewState: LiveData<ViewState> = _cityLocationViewState

    fun onCitySelected(city: City) {
        _selectedCity.value = city

        compositeDisposable.add(
            fetchCityLocationUseCase.execute(city)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe {
                _cityLocationViewState.value = ViewState.InProgress
            }
            .subscribeBy(
                onSuccess = {
                    _cityLocationViewState.value = ViewState.CityPos(it)
                },
                onError = {
                    _cityLocationViewState.value = ViewState.Error(it)
                    Timber.e(it, "Error while fetching city location: $city")
                }
            )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    sealed class ViewState {
        object InProgress : ViewState()
        data class Error(val error: Throwable) : ViewState()
        data class CityPos(val cityPos: CityLocation) : ViewState()
    }

}

