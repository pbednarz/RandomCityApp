package pl.pbednarz.randomcityapp.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.withArguments(crossinline block: Bundle.() -> Unit): T {
    arguments = Bundle().apply(block)
    return this
}