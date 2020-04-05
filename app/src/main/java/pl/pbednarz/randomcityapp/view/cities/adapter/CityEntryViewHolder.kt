package pl.pbednarz.randomcityapp.view.cities.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_city.view.*
import pl.pbednarz.randomcityapp.domain.City
import java.text.SimpleDateFormat
import java.util.*

class CityEntryViewHolder(
    override val containerView: View,
    clickListener: OnCityClickListener
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private var bindCityColor: City? = null

    init {
        containerView.setOnClickListener {
            bindCityColor?.let { clickListener.onCityClicked(it) }
        }
    }

    fun bind(cityColor: City) {
        this.bindCityColor = cityColor
        containerView.cityName.apply {
            text = cityColor.name
            setTextColor(cityColor.color)
        }
        containerView.addedAt.apply {
            text = simpleDateFormat.format(cityColor.createdAt)
            setTextColor(cityColor.color)
        }
    }
}