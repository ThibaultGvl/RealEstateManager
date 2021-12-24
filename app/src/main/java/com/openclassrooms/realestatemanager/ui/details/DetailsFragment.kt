package com.openclassrooms.realestatemanager.ui.details

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.insert.InsertActivity


class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var detailsBinding: FragmentDetailsBinding

    private lateinit var detailsViewModel: DetailsViewModel

    private var mPropertyId: Long = 1

    private lateinit var mFab: FloatingActionButton

   private var mPhotos: ArrayList<String> = ArrayList()

   private lateinit var mRecyclerView: RecyclerView

   private lateinit var mAdapter: PhotosAdapter

    private lateinit var mGoogleMap: GoogleMap

    companion object {
        const val ARG_NAME = "id"


        fun newInstance(id: Long): DetailsFragment {
            val fragment = DetailsFragment()

            val bundle = Bundle().apply {
                putLong(ARG_NAME, id)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        detailsBinding = FragmentDetailsBinding.inflate(layoutInflater)
        val view = detailsBinding.root
        val id = arguments?.getLong("id")
        if (id != null) {
            mPropertyId = id
        }
        val injection = Injection::class.java
        val mViewModelFactory = injection.newInstance()
                .provideViewModelFactory(this.requireContext())
        detailsViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(DetailsViewModel::class.java)
        updateWithProperty(mPropertyId)
        mFab = detailsBinding.fab
        mFab.setOnClickListener {
            val intent = Intent(activity?.applicationContext, InsertActivity::class.java)
            intent.putExtra("id", mPropertyId)
            startActivity(intent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateWithProperty(mPropertyId)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.getMapAsync(this)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateWithProperty(id: Long) {
        detailsViewModel.getPropertyById(id).observe(viewLifecycleOwner, this::getProperty)
    }

    override fun onResume() {
        super.onResume()
        updateWithProperty(mPropertyId)
    }

    private fun getProperty(property: Property) {
        val photosUri = ArrayList<Uri>()
        val photosString = property.photos
        val photoStringArray = photosString.trim().splitToSequence(',').filter { it.isNotEmpty() }
                .toList()
        for (photo in photoStringArray) {
            var photoToAdd = photo
            for (char in photo) {
                photoToAdd = when {
                    char.toString() == "[" -> {
                        photoToAdd.replace("[","")
                    }
                    char.toString() == "]" -> {
                        photoToAdd.replace("]","")
                    }
                    else -> {
                        photoToAdd.replace(" ", "")
                    }
                }
            }
            if (photoToAdd.isNotEmpty()) {
                photosUri.add(Uri.parse(photoToAdd))
            }
        }
        mAdapter = PhotosAdapter(photosUri)
        mRecyclerView = detailsBinding.carouselView
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.adapter = mAdapter
        detailsBinding.description.text = property.description
        detailsBinding.piece.text = property.piece.toString()
        detailsBinding.surface.text = property.surface.toString()
        detailsBinding.position.text = property.address
        val location = getLocationFromAddress(context, property.address)
        location?.let { CameraUpdateFactory.newLatLngZoom(it, 15F) }?.let { mGoogleMap.moveCamera(it) }
        location?.let { MarkerOptions().position(it) }?.let { mGoogleMap.addMarker(it) }
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
    }

    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 1)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            p1 = LatLng(location.latitude, location.longitude)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return p1
    }
}