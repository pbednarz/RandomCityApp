package pl.pbednarz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.pbednarz.database.converters.TimestampConverter
import pl.pbednarz.database.dao.CitiesDao
import pl.pbednarz.database.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = true)
@TypeConverters(TimestampConverter::class)
abstract class CitiesDatabase : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
}