package pl.pbednarz.randomcityapp.view.cities.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_city_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.pbednarz.randomcityapp.R
import pl.pbednarz.randomcityapp.domain.City
import pl.pbednarz.randomcityapp.domain.CityWithPosition
import pl.pbednarz.randomcityapp.extensions.visible
import pl.pbednarz.randomcityapp.extensions.withArguments
import timber.log.Timber


class CityDetailsFragment : Fragment(), OnMapReadyCallback {

    private val detailsViewModel: CityDetailsViewModel by viewModel()

    private lateinit var selectedCity: City

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedCity = arguments?.getParcelable(ARG_SELECTED_CITY)
            ?: throw IllegalStateException("selected city is missing")
        detailsViewModel.onCitySelected(selectedCity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        Timber.d("API key for Google maps: ${getString(R.string.google_maps_key)}")
        mapFragment?.getMapAsync(this)
        detailsViewModel.selectedCity.observe(this, Observer {
            detailsToolbar.title = it.name
            detailsToolbar.setBackgroundColor(it.color)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? CityDetailsActivity)?.setupToolbar(detailsToolbar)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        detailsViewModel.cityLocationViewState.observe(this, Observer { state ->
            errorView.visible = state.errorVisible
            progressBar.visible = state.progressVisible
            if (state.cityPos != null) {
                showCityOnMap(state.cityPos)
            }
        })
    }

    private fun showCityOnMap(cityWithPos: CityWithPosition) {
        val mapPos = LatLng(
            cityWithPos.latLng.lat,
            cityWithPos.latLng.lng
        )
        mMap.addMarker(
            MarkerOptions()
                .position(mapPos)
                .title(cityWithPos.city.name)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPos, MAP_ZOOM))
    }

    companion object {
        private const val MAP_ZOOM = 12f
        const val ARG_SELECTED_CITY = "ARG_SELECTED_CITY"

        fun newInstance(selectedCityColor: City): CityDetailsFragment {
            return CityDetailsFragment()
                .withArguments {
                    putParcelable(ARG_SELECTED_CITY, selectedCityColor)
                }
        }
    }
}
