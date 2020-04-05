package pl.pbednarz.randomcityapp

import io.reactivex.schedulers.TestScheduler
import pl.pbednarz.randomcityapp.di.SchedulersProvider

class TestSchedulersProvider : SchedulersProvider {

    private val testScheduler = TestScheduler()

    override fun io() = testScheduler

    override fun computation() = testScheduler

    override fun ui() = testScheduler

    fun triggerActions() {
        testScheduler.triggerActions()
    }
}
