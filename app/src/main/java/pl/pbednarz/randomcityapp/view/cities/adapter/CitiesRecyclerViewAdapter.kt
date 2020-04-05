package pl.pbednarz.randomcityapp.view.cities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.pbednarz.randomcityapp.R
import pl.pbednarz.randomcityapp.domain.City

class CitiesRecyclerViewAdapter : RecyclerView.Adapter<CityEntryViewHolder>() {

    lateinit var onCityClickListener: OnCityClickListener

    private val items: MutableList<City> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    fun setItems(newItems: List<City>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityEntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_city, parent, false)
        return CityEntryViewHolder(view, onCityClickListener)
    }

    override fun onBindViewHolder(holder: CityEntryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getItemCount() = items.size

}