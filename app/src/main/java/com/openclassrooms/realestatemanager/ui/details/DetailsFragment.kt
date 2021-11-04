package com.openclassrooms.realestatemanager.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.insert.InsertActivity

class DetailsFragment : Fragment() {

    private lateinit var detailsBinding: FragmentDetailsBinding

    private lateinit var detailsViewModel: DetailsViewModel

    private var mPropertyId: Long = 1

    private lateinit var mFab: FloatingActionButton

    private var mPhotos: ArrayList<String> = ArrayList()

    private lateinit var mRecyclerView: RecyclerView

    private var mAdapter = PhotosAdapter(mPhotos)

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
        val detailsInjection = DetailsInjection::class.java
        val mViewModelFactory = detailsInjection.newInstance()
                .provideViewModelFactory(this.requireContext())
        detailsViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(DetailsViewModel::class.java)
        updateWithProperty(mPropertyId)
        mRecyclerView = detailsBinding.carouselView
        mRecyclerView.adapter = mAdapter
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
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
        mPhotos.addAll(property.photos)
        detailsBinding.description.text = property.description
        detailsBinding.piece.text = property.piece.toString()
        detailsBinding.surface.text = property.surface.toString()
        detailsBinding.position.text = property.address

    }
}