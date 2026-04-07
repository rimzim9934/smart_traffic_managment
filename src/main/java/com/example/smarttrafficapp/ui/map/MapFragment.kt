package com.example.smarttrafficapp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smarttrafficapp.R
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.seedData()

        val existingFragment = childFragmentManager.findFragmentByTag("map")

        val mapFragment = if (existingFragment != null) {
            existingFragment as SupportMapFragment
        } else {
            SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(R.id.map_container, it, "map")
                    .commit()
            }
        }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.junctions.observe(viewLifecycleOwner) { junctions ->
            googleMap.clear()
            junctions.forEach { junction ->
                val location = LatLng(junction.latitude, junction.longitude)
                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(junction.name)
                )
                marker?.tag = junction
            }

            // Zoom into the first junction if available
            junctions.firstOrNull()?.let { first ->
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(first.latitude, first.longitude), 12f
                    )
                )
            }
        }

        googleMap.setOnMarkerClickListener { marker ->
            val junction = marker.tag as? com.example.smarttrafficapp.domain.model.Junction
            junction?.let {
                val bundle = Bundle().apply {
                    putInt("junctionId", it.id)
                    putString("junctionName", it.name)
                }
                findNavController().navigate(R.id.dashboardFragment, bundle)
            }
            true
        }
    }
}
