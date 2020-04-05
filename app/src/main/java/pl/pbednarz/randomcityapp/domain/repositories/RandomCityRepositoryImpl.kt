package pl.pbednarz.randomcityapp.domain.repositories

import io.reactivex.Single
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.mappers.ColorNameMapper
import java.util.*

class RandomCityRepositoryImpl(
    private val colorNameMapper: ColorNameMapper
) : RandomCityRepository {

    private val cities =
        listOf("Gdańsk", "Warszawa", "Poznań", "Białystok", "Wrocław", "Katowice", "Kraków")
    private val colors = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")

    private fun getRandomCityAndColor(): RandomCityWithColor {
        return RandomCityWithColor(
            cityName = cities.random(),
            colorName = colors.random()
        )
    }

    override fun getRandomCity(): Single<City> {
        return Single.fromCallable { getRandomCityAndColor() }
            .map {
                City(
                    id = 0,
                    name = it.cityName,
                    color = colorNameMapper.map(it.colorName),
                    createdAt = Date()
                )
            }
    }

    private data class RandomCityWithColor(
        val cityName: String,
        val colorName: String
    )
}