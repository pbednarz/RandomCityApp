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
                _cityLocationViewState.value = ViewState(progressVisible = true)
            }
            .subscribeBy(
                onSuccess = {
                    _cityLocationViewState.value = ViewState(cityPos = it)
                },
                onError = {
                    _cityLocationViewState.value = ViewState(errorVisible = true)
                    Timber.e(it, "Error while fetching city location: $city")
                }
            )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    data class ViewState(
        val progressVisible: Boolean = false,
        val errorVisible: Boolean = false,
        val cityPos: CityLocation? = null
    )

}

