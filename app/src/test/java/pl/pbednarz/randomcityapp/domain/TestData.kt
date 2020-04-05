package pl.pbednarz.randomcityapp.domain

import android.graphics.Color
import pl.pbednarz.database.entity.CityEntity
import java.util.*

object TestData {

    private val timeStamp = System.currentTimeMillis()

    val city = City(
        id = 1L,
        name = "Warsaw",
        color = Color.RED,
        createdAt = Date(timeStamp)
    )

    val cityEntity = CityEntity(
        id = 1L,
        cityName = "Warsaw",
        color = Color.RED,
        createdAt = Date(timeStamp)
    )
}
