package pl.pbednarz.randomcityapp.view.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import pl.pbednarz.randomcityapp.R
import pl.pbednarz.randomcityapp.view.cities.CityListActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.newCityLiveData.observe(this, Observer { city ->
            startActivity(CityListActivity.newInstance(this, city))
            finish()
        })
    }
}