package com.example.bencanatracker.ui.map

import android.os.Bundle
import com.google.android.libraries.places.api.Places
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bencanatracker.R
import com.example.bencanatracker.databinding.FragmentMapBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private var mGoogleMap:GoogleMap? = null
    private lateinit var autoCompleteFragment: AutocompleteSupportFragment

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapfragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Places.initialize(activity?.applicationContext, getString(R.string.google_map_api_key))
        autoCompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment
        autoCompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG))
        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceSelected(place: Place) {
                val latlng = place.latLng
                zoomOnMap(latlng)
            }

        })

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }

    private fun zoomOnMap(latlng: LatLng) {
        val newLatlngZoom = CameraUpdateFactory.newLatLngZoom(latlng, 12f)
        mGoogleMap?.animateCamera(newLatlngZoom)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}