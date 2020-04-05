package pl.pbednarz.randomcityapp.view.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.subjects.PublishSubject
import org.junit.Rule
import org.junit.Test
import pl.pbednarz.randomcityapp.CitiesProducerService
import pl.pbednarz.randomcityapp.TestSchedulersProvider
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.TestData
import pl.pbednarz.randomcityapp.livedata.Event


class SplashViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testSchedulers: TestSchedulersProvider = TestSchedulersProvider()

    private val testPublisher: PublishSubject<City> = PublishSubject.create()

    private val citiesProducerService: CitiesProducerService = mock {
        on(it.newCityEvent()) doReturn testPublisher
    }

    private val viewModel: SplashViewModel = SplashViewModel(
        citiesProducerService = citiesProducerService,
        schedulersProvider = testSchedulers
    )

    @Test
    fun `given viewModel is initialized, when new city is produced, then produce new City event`() {
        testPublisher.onNext(TestData.city)

        testSchedulers.triggerActions()

        viewModel.newCityLiveData
            .test()
            .assertValue(Event(TestData.city))
    }
}