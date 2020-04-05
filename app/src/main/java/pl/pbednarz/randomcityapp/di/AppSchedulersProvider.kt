package pl.pbednarz.randomcityapp.di

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulersProvider : SchedulersProvider {

    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun ui() = AndroidSchedulers.mainThread()
}
