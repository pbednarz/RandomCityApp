package pl.pbednarz.randomcityapp.view.cities.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.pbednarz.randomcityapp.di.SchedulersProvider
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.CityWithPosition
import pl.pbednarz.randomcityapp.domain.repositories.CityLocationRepository
import timber.log.Timber

class CityDetailsViewModel(
    private val cityLocationRepository: CityLocationRepository,
    private val schedulersProvider: SchedulersProvider
) : ViewModel() {

    private val _selectedCity: MutableLiveData<City> = MutableLiveData()
    private val _cityLocationViewState: MutableLiveData<ViewState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    val selectedCity: LiveData<City> = _selectedCity
    val cityLocationViewState: LiveData<ViewState> = _cityLocationViewState

    fun onCitySelected(city: City) {
        _selectedCity.value = city

        compositeDisposable.add(cityLocationRepository.getCityLocation(city)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe {
                _cityLocationViewState.value = ViewState(progressVisible = true)
            }
            .subscribeBy(
                onSuccess = {
                    _cityLocationViewState.value = ViewState(cityPos = CityWithPosition(city, it))
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
        val cityPos: CityWithPosition? = null
    )

}

