package pl.pbednarz.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import pl.pbednarz.database.entity.CityEntity

@Dao
interface CitiesDao {
    @Query("SELECT * FROM cities ORDER BY cityName")
    fun getCitiesByName(): LiveData<List<CityEntity>>

    @Query("SELECT * FROM cities WHERE id = :cityId")
    fun getCity(cityId: String): LiveData<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(cities: List<CityEntity>): Completable
}