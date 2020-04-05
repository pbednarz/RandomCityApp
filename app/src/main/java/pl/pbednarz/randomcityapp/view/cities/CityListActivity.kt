package pl.pbednarz.randomcityapp.view.cities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_city_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.pbednarz.randomcityapp.R
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.view.cities.adapter.CitiesRecyclerViewAdapter
import pl.pbednarz.randomcityapp.view.cities.adapter.OnCityClickListener
import pl.pbednarz.randomcityapp.view.cities.details.CityDetailsActivity
import pl.pbednarz.randomcityapp.view.cities.details.CityDetailsFragment

class CityListActivity : AppCompatActivity() {

    private val listViewModel: CitiesListViewModel by viewModel()

    private val adapter: CitiesRecyclerViewAdapter = CitiesRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)
        setupRecyclerView(item_list)

        listViewModel.cities.observe(this, Observer {
            adapter.setItems(it)
        })
        listViewModel.citySelected.observe(this, Observer {
            openCityDetails(it)
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter.onCityClickListener = object : OnCityClickListener {
            override fun onCityClicked(city: City) {
                listViewModel.onCitySelected(city)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun openCityDetails(city: City) {
        if (item_detail_container != null) {
            openDetailsFragmentBySide(city)
        } else {
            openDetailsActivity(city)
        }
    }

    private fun openDetailsFragmentBySide(selectedCityColor: City) {
        val fragment = CityDetailsFragment.newInstance(selectedCityColor)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_detail_container, fragment)
            .commit()
    }

    private fun openDetailsActivity(selectedCityColor: City) {
        val intent = CityDetailsActivity.newInstance(this, selectedCityColor)
        startActivity(intent)
    }

    companion object {
        private const val EXTRA_SELECTED_CITY = "EXTRA_SELECTED_CITY"

        fun newInstance(context: Context, city: City): Intent {
            return Intent(context, CityListActivity::class.java).apply {
                putExtra(EXTRA_SELECTED_CITY, city)
            }
        }
    }
}
