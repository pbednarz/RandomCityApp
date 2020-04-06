package pl.pbednarz.randomcityapp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import pl.pbednarz.randomcityapp.di.SchedulersProvider
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.usecases.GenerateRandomCityUseCase
import timber.log.Timber
import java.util.concurrent.TimeUnit


class CitiesProducerService(
    private val generateRandomCityUseCase: GenerateRandomCityUseCase,
    private val schedulersProvider: SchedulersProvider
) : LifecycleObserver {

    private val randomCityPublisher: PublishSubject<City> = PublishSubject.create()

    private var timerDisposable: Disposable? = null

    fun newCityEvent(): Observable<City> {
        return randomCityPublisher.hide()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        timerDisposable?.dispose()
        timerDisposable =
            Observable.interval(TIMER_INTERVAL_SEC, TIMER_INTERVAL_SEC, TimeUnit.SECONDS)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.io())
                .flatMap { generateRandomCityUseCase.execute().toObservable() }
                .subscribeBy(
                    onNext = { nextValue ->
                        Timber.d("New city generated: $nextValue")
                        randomCityPublisher.onNext(nextValue)
                    },
                    onError = {
                        Timber.e(it, "Error while generating a new city entry")
                    }
                )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        timerDisposable?.dispose()
    }

    companion object {
        private const val TIMER_INTERVAL_SEC = 5L
    }
}
