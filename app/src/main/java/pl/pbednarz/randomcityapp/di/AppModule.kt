package pl.pbednarz.randomcityapp.di


import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.pbednarz.database.CitiesDatabase
import pl.pbednarz.randomcityapp.CitiesProducerService
import pl.pbednarz.randomcityapp.domain.mappers.CityDomainMapper
import pl.pbednarz.randomcityapp.domain.mappers.CityEntityMapper
import pl.pbednarz.randomcityapp.domain.mappers.ColorNameMapper
import pl.pbednarz.randomcityapp.domain.repositories.*
import pl.pbednarz.randomcityapp.domain.usecases.FetchCityLocationUseCase
import pl.pbednarz.randomcityapp.domain.usecases.GenerateRandomCityUseCase
import pl.pbednarz.randomcityapp.view.cities.CitiesListViewModel
import pl.pbednarz.randomcityapp.view.cities.details.CityDetailsViewModel
import pl.pbednarz.randomcityapp.view.splash.SplashViewModel

@JvmField
val appModule = module {

    single<SchedulersProvider> { AppSchedulersProvider() }

    single { Room.databaseBuilder(get(), CitiesDatabase::class.java, "cities_db").build() }

    single { get<CitiesDatabase>().citiesDao() }

    single { ColorNameMapper() }

    single { CityDomainMapper() }

    single { CityEntityMapper() }

    single { CitiesProducerService(get(), get()) }

    single<CityLocationRepository> {
        CityLocationRepositoryImpl()
    }

    single<RandomCityRepository> {
        RandomCityRepositoryImpl(get())
    }

    single<CitiesRepository> {
        CitiesRepositoryImpl(get(), get(), get())
    }

    factory { GenerateRandomCityUseCase(get(), get()) }

    factory { FetchCityLocationUseCase(get()) }

    viewModel { SplashViewModel(get(), get()) }

    viewModel { CitiesListViewModel(get()) }

    viewModel { CityDetailsViewModel(get(), get()) }
}