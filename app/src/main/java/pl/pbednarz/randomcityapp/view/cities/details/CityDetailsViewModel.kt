package pl.pbednarz.randomcityapp.view.cities.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.CityWithPosition
import pl.pbednarz.randomcityapp.domain.repositories.CityLocationRepository
import timber.log.Timber

class CityDetailsViewModel(
    private val cityLocationRepository: CityLocationRepository
) : ViewModel() {

    private val _selectedCity: MutableLiveData<City> = MutableLiveData()
    private val _cityLocationState: MutableLiveData<State> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    val selectedCity: LiveData<City> = _selectedCity
    val cityLocationState: LiveData<State> = _cityLocationState

    fun onCitySelected(city: City) {
        _selectedCity.value = city

        compositeDisposable.add(cityLocationRepository.getCityLocation(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _cityLocationState.value = State(progressVisible = true)
            }
            .subscribeBy(
                onSuccess = {
                    _cityLocationState.value = State(cityPos = CityWithPosition(city, it))
                },
                onError = {
                    _cityLocationState.value = State(errorVisible = true)
                    Timber.e(it, "Error while fetching city location: $city")
                }
            )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    data class State(
        val progressVisible: Boolean = false,
        val errorVisible: Boolean = false,
        val cityPos: CityWithPosition? = null
    )

}

