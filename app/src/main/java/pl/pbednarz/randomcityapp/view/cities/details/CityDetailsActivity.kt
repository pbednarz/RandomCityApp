package pl.pbednarz.randomcityapp.view.cities.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pl.pbednarz.randomcityapp.R
import pl.pbednarz.randomcityapp.domain.City

class CityDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        val selectedCity: City = intent.getParcelableExtra(EXTRA_SELECTED_CITY) ?: return finish()
        if (savedInstanceState == null) {
            val fragment = CityDetailsFragment.newInstance(selectedCity)
            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = toolbar.title
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_SELECTED_CITY = "EXTRA_SELECTED_CITY"

        fun newInstance(context: Context, cityEntity: City): Intent {
            return Intent(context, CityDetailsActivity::class.java).apply {
                putExtra(EXTRA_SELECTED_CITY, cityEntity)
            }
        }
    }
}
