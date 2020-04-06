package pl.pbednarz.randomcityapp.domain.usecases

import io.reactivex.Single
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.repositories.CitiesRepository
import pl.pbednarz.randomcityapp.domain.repositories.RandomCityRepository

class GenerateRandomCityUseCase(
    private val citiesRepository: CitiesRepository,
    private val randomCityRepository: RandomCityRepository
) : OutUseCase<Single<City>> {

    override fun execute(): Single<City> {
        return randomCityRepository.getRandomCity()
            .flatMap { newCity ->
                citiesRepository.saveCity(newCity)
                    .andThen(Single.just(newCity))
            }
    }
}