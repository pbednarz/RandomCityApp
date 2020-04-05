package pl.pbednarz.randomcityapp.domain.mappers

import android.graphics.Color
import junit.framework.Assert.assertEquals
import org.junit.Test
import pl.pbednarz.database.entity.CityEntity
import pl.pbednarz.randomcityapp.domain.City
import java.util.*

class CityDomainMapperTest {

    private val cityDomainMapper = CityDomainMapper()

    @Test
    fun `given CityDomainMapper, when map() is applied, then return correct City entity`() {
        val timeStamp = System.currentTimeMillis()
        val actual = City(
            id = 0L,
            name = "testName",
            color = Color.RED,
            createdAt = Date(timeStamp)
        )

        val expected = CityEntity(
            id = 0L,
            cityName = "testName",
            color = Color.RED,
            createdAt = Date(timeStamp)
        )

        assertEquals(expected, cityDomainMapper.map(actual))
    }
}