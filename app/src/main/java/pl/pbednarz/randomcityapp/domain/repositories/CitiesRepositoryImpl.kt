package pl.pbednarz.randomcityapp.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Completable
import pl.pbednarz.database.dao.CitiesDao
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.mappers.CityDomainMapper
import pl.pbednarz.randomcityapp.domain.mappers.CityEntityMapper
import pl.pbednarz.randomcityapp.domain.mappers.mapAll

class CitiesRepositoryImpl(
    private val citiesDao: CitiesDao,
    private val entityMapper: CityEntityMapper,
    private val domainMapper: CityDomainMapper
) : CitiesRepository {

    override fun getCitiesByName(): LiveData<List<City>> {
        return Transformations.map(citiesDao.getCitiesByName()) { entities ->
            entityMapper.mapAll(entities)
        }
    }

    override fun saveCity(city: City): Completable {
        val cityEntity = domainMapper.map(city)
        return citiesDao.insertCity(cityEntity)
    }
}