package pl.pbednarz.randomcityapp.view.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.TestData
import pl.pbednarz.randomcityapp.domain.repositories.CitiesRepository
import pl.pbednarz.randomcityapp.livedata.Event

class CitiesListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val citiesLiveData: MutableLiveData<List<City>> = MutableLiveData(emptyList())

    private val citiesRepository: CitiesRepository = mock {
        on(it.getCitiesByName()) doReturn citiesLiveData
    }

    private val viewModel: CitiesListViewModel = CitiesListViewModel(
        citiesRepository = citiesRepository
    )

    @Test
    fun `given viewModel is initialized, when repository emits new cities, then observe new values`() {
        val newCities = listOf(
            TestData.city.copy(id = 1),
            TestData.city.copy(id = 2)
        )

        citiesLiveData.postValue(newCities)

        viewModel.cities
            .test()
            .assertValue(newCities)
    }

    @Test
    fun `given viewModel is initialized, when new city is selected, then produce City selected event`() {
        viewModel.onCitySelected(TestData.city)

        viewModel.citySelected
            .test()
            .assertValue(Event(TestData.city))
    }
}