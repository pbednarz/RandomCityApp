package pl.pbednarz.randomcityapp.view.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.repositories.CitiesRepository
import pl.pbednarz.randomcityapp.livedata.Event

class CitiesListViewModel(
    citiesRepository: CitiesRepository
) : ViewModel() {

    private val _cities: LiveData<List<City>> = citiesRepository.getCitiesByName()

    private val _citySelectedEvent = MutableLiveData<Event<City>>()

    val citySelected: LiveData<Event<City>> = _citySelectedEvent

    val cities: LiveData<List<City>> = _cities

    fun onCitySelected(city: City) {
        _citySelectedEvent.postValue(Event(city))
    }
}