package pl.pbednarz.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pl.pbednarz.database.converters.TimestampConverter
import java.util.*

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    val cityName: String,

    val color: Int,

    @TypeConverters(TimestampConverter::class)
    val emittedAt: Date
)