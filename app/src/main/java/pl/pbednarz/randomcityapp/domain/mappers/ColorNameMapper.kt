package pl.pbednarz.randomcityapp.domain.mappers

import android.graphics.Color

class ColorNameMapper : Mapper<String, Int> {
    override fun map(from: String): Int {
        return when (from) {
            "Yellow" -> Color.YELLOW
            "Green" -> Color.GREEN
            "Blue" -> Color.BLUE
            "Red" -> Color.RED
            "Black" -> Color.BLACK
            "White" -> Color.WHITE
            else -> throw IllegalStateException("Couldn't parse color $from")
        }
    }
}