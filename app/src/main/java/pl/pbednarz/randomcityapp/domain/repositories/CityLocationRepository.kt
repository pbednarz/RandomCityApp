package pl.pbednarz.randomcityapp.domain.repositories

import io.reactivex.Single
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.LatLng

interface CityLocationRepository {

    fun getCityLocation(city: City): Single<LatLng>
}