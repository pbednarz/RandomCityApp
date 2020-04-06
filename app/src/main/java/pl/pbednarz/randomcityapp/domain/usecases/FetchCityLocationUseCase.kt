package pl.pbednarz.randomcityapp.domain.usecases

import io.reactivex.Single
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.CityLocation
import pl.pbednarz.randomcityapp.domain.repositories.CityLocationRepository

class FetchCityLocationUseCase(
    private val cityLocationRepository: CityLocationRepository
) : InOutUseCase<City, Single<CityLocation>> {

    override fun execute(input: City): Single<CityLocation> {
        return cityLocationRepository.getCityLocation(input)
            .map { CityLocation(input, it) }
    }
}