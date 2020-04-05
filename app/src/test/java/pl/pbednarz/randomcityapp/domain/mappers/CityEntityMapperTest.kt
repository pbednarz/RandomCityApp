package pl.pbednarz.randomcityapp.domain.mappers

import android.graphics.Color
import junit.framework.Assert.assertEquals
import org.junit.Test
import pl.pbednarz.database.entity.CityEntity
import pl.pbednarz.randomcityapp.domain.City
import java.util.*

class CityEntityMapperTest {

    private val cityEntityMapper = CityEntityMapper()

    @Test
    fun `given CityEntityMapper, when map() is applied, then return correct City domain model`() {
        val timeStamp = System.currentTimeMillis()
        val actual = CityEntity(
            id = 0L,
            cityName = "testName",
            color = Color.RED,
            createdAt = Date(timeStamp)
        )

        val expected = City(
            id = 0L,
            name = "testName",
            color = Color.RED,
            createdAt = Date(timeStamp)
        )

        assertEquals(expected, cityEntityMapper.map(actual))
    }
}