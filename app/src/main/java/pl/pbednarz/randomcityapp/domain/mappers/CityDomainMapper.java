package pl.pbednarz.randomcityapp.domain.mappers;

import pl.pbednarz.database.entity.CityEntity;
import pl.pbednarz.randomcityapp.domain.City;

public class CityDomainMapper implements Mapper<City, CityEntity> {

    @Override
    public CityEntity map(City from) {
        return new CityEntity(
                from.getId(),
                from.getName(),
                from.getColor(),
                from.getCreatedAt()
        );
    }
}
