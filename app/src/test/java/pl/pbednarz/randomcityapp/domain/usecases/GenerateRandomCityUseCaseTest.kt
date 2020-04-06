package pl.pbednarz.randomcityapp.domain.usecases

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import pl.pbednarz.randomcityapp.domain.TestData
import pl.pbednarz.randomcityapp.domain.repositories.CitiesRepository
import pl.pbednarz.randomcityapp.domain.repositories.RandomCityRepository

class GenerateRandomCityUseCaseTest {

    private val citiesRepository: CitiesRepository = mock()

    private val randomCityRepository: RandomCityRepository = mock()

    private val useCase = GenerateRandomCityUseCase(
        citiesRepository = citiesRepository,
        randomCityRepository = randomCityRepository
    )

    @Test
    fun `given random City is fetched, when useCase executed, then save and return random city`() {
        val randomTestCity = TestData.city
        whenever(randomCityRepository.getRandomCity()).thenReturn(Single.just(randomTestCity))
        whenever(citiesRepository.saveCity(eq(randomTestCity))).thenReturn(Completable.complete())

        useCase.execute()
            .test()
            .assertValue(randomTestCity)

        verify(randomCityRepository).getRandomCity()
        verify(citiesRepository).saveCity(randomTestCity)
    }

    @Test
    fun `given random City fetch fails, when useCase is executed, then returns error`() {
        val testException = RuntimeException("repository fails")
        whenever(randomCityRepository.getRandomCity()).thenReturn(Single.error(testException))

        useCase.execute()
            .test()
            .assertError(testException)

        verify(randomCityRepository).getRandomCity()
        verify(citiesRepository, never()).saveCity(any())
    }
}