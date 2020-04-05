package pl.pbednarz.randomcityapp.domain.mappers

import pl.pbednarz.database.entity.CityEntity
import pl.pbednarz.randomcityapp.domain.City

class CityEntityMapper : Mapper<CityEntity, City> {
    override fun map(from: CityEntity): City {
        return City(
            id = from.id,
            name = from.cityName,
            color = from.color,
            createdAt = from.emittedAt
        )
    }
}