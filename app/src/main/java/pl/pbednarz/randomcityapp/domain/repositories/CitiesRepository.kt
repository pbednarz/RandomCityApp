package pl.pbednarz.randomcityapp.domain.repositories

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import pl.pbednarz.randomcityapp.domain.City

interface CitiesRepository {

    fun getCitiesByName(): LiveData<List<City>>

    fun saveCity(city: City): Completable
}