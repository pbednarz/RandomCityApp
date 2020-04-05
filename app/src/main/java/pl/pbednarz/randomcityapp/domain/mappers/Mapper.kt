package pl.pbednarz.randomcityapp.domain.mappers

interface Mapper<in F, out T> {
    fun map(from: F): T
}

fun <F, T> Mapper<F, T>.mapAll(from: List<F>): List<T> = from.map { map(it) }