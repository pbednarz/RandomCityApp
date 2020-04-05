package pl.pbednarz.randomcityapp.domain.repositories

import io.reactivex.Single
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.LatLng

class CityLocationRepositoryImpl : CityLocationRepository {

    private val locationsMap: Map<String, LatLng> = mapOf(
        "Gdańsk" to LatLng(
            lat = 54.372158,
            lng = 18.638306
        ),
        "Warszawa" to LatLng(
            lat = 52.237049,
            lng = 21.017532
        ),
        "Poznań" to LatLng(
            lat = 52.409538,
            lng = 16.931992
        ),
        "Białystok" to LatLng(
            lat = 53.13333,
            lng = 23.16433
        ),
        "Wrocław" to LatLng(
            lat = 51.107883,
            lng = 17.038538
        ),
        "Katowice" to LatLng(
            lat = 50.270908,
            lng = 19.039993
        ),
        "Kraków" to LatLng(
            lat = 50.049683,
            lng = 19.944544
        )
    )

    override fun getCityLocation(city: City): Single<LatLng> {
        return Single.fromCallable {
            locationsMap[city.name]
                ?: throw IllegalStateException("couldn't find pos for city $city")
        }
    }
}