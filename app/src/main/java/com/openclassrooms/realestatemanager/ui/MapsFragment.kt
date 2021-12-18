package com.openclassrooms.realestatemanager.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMapsBinding
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment
import com.openclassrooms.realestatemanager.ui.details.DetailsInjection
import com.openclassrooms.realestatemanager.ui.details.DetailsViewModel


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMapsBinding: FragmentMapsBinding

    private lateinit var mMap: GoogleMap

    private var PERMISSION_ID = 1

    private var mLocationPermission = false

    lateinit var mLocationManager: LocationManager

    lateinit var mapsViewModel: MapsViewModel

    private var mProperties: ArrayList<com.openclassrooms.realestatemanager.model.Property> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mMapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
        val detailsInjection = DetailsInjection::class.java
        val mViewModelFactory = detailsInjection.newInstance()
                .provideViewModelFactory(this.requireContext())
        mapsViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(MapsViewModel::class.java)
        mapsViewModel.initProperties()
        mapsViewModel.getProperties()?.observe(viewLifecycleOwner, this::initProperties)
        mLocationManager = context?.getSystemService(Context.LOCATION_SERVICE)!! as LocationManager
        return mMapsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        mLocationManager = context?.getSystemService(Context.LOCATION_SERVICE)!! as LocationManager
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        getLocationPermission()
    }

    private fun getLocationPermission(){
        if ((context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED && context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED)) {
            mLocationPermission = true
            val mLocationGPS: Location? = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val latLng = mLocationGPS?.let { LatLng(it.latitude, mLocationGPS.longitude) }
            latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 17F) }?.let { mMap.moveCamera(it) }
            for(property in mProperties) {
                val position = DetailsFragment().getLocationFromAddress(context, property.address)
                position?.let { MarkerOptions().position(it) }?.let { mMap.addMarker(it) }
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID)
        }
    }

    private fun initProperties(list: List<com.openclassrooms.realestatemanager.model.Property>) {
        mProperties.clear()
        mProperties.addAll(list)
    }
}