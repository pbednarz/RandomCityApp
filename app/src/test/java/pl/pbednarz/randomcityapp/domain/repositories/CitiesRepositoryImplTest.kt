package pl.pbednarz.randomcityapp.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import org.junit.Test
import pl.pbednarz.database.dao.CitiesDao
import pl.pbednarz.randomcityapp.domain.TestData
import pl.pbednarz.randomcityapp.domain.mappers.CityDomainMapper
import pl.pbednarz.randomcityapp.domain.mappers.CityEntityMapper

class CitiesRepositoryImplTest {

    private val city = TestData.city
    private val cityEntity = TestData.cityEntity
    private val citiesDao: CitiesDao = mock {
        on(it.getCitiesByName()) doReturn MutableLiveData(emptyList())
        on(it.insertCity(any())) doReturn Completable.complete()
    }
    private val entityMapper: CityEntityMapper = mock {
        on(it.map(any())).thenReturn(city)
    }
    private val domainMapper: CityDomainMapper = mock {
        on(it.map(any())).thenReturn(cityEntity)
    }

    private val citiesRepository = CitiesRepositoryImpl(
        citiesDao = citiesDao,
        entityMapper = entityMapper,
        domainMapper = domainMapper
    )

    @Test
    fun `when saveCity() is executed then save mapped entity in database`() {
        citiesRepository.saveCity(city)

        verify(citiesDao).insertCity(cityEntity)
    }

    @Test
    fun `when getCitiesByName() is executed then fetch data from database`() {
        citiesRepository.getCitiesByName()

        verify(citiesDao).getCitiesByName()
    }
}