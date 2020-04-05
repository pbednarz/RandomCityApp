package pl.pbednarz.randomcityapp.view.cities.adapter

import pl.pbednarz.randomcityapp.domain.City

interface OnCityClickListener {
    fun onCityClicked(city: City)
}