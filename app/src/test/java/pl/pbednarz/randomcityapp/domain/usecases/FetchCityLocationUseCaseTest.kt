package pl.pbednarz.randomcityapp.domain.usecases

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import pl.pbednarz.randomcityapp.domain.CityLocation
import pl.pbednarz.randomcityapp.domain.TestData
import pl.pbednarz.randomcityapp.domain.repositories.CityLocationRepository

class FetchCityLocationUseCaseTest {

    private val cityLocationRepository: CityLocationRepository = mock()

    private val useCase = FetchCityLocationUseCase(
        cityLocationRepository = cityLocationRepository
    )

    @Test
    fun `given location is fetched, when useCase executed, then return City with location`() {
        val testCity = TestData.city
        val testLocation = TestData.location
        val expected = CityLocation(
            city = testCity,
            latLng = testLocation
        )
        whenever(cityLocationRepository.getCityLocation(eq(testCity))).thenReturn(
            Single.just(
                testLocation
            )
        )

        useCase.execute(testCity)
            .test()
            .assertValue(expected)

        verify(cityLocationRepository).getCityLocation(testCity)
    }

    @Test
    fun `given location is fetch fails, when useCase executed, then return error`() {
        val testCity = TestData.city
        val testError = RuntimeException("location fetch fails")

        whenever(cityLocationRepository.getCityLocation(eq(testCity))).thenReturn(
            Single.error(
                testError
            )
        )

        useCase.execute(testCity)
            .test()
            .assertError(testError)

        verify(cityLocationRepository).getCityLocation(testCity)
    }
}