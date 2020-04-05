package pl.pbednarz.randomcityapp.domain.repositories

import io.reactivex.Single
import pl.pbednarz.randomcityapp.domain.City

interface RandomCityRepository {

    fun getRandomCity(): Single<City>
}