package pl.pbednarz.randomcityapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class City(
    val id: Long,
    val name: String,
    val color: Int,
    val createdAt: Date
) : Parcelable