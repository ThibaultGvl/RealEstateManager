package com.openclassrooms.realestatemanager.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityDetailsBinding
import com.openclassrooms.realestatemanager.model.Property
import java.io.Serializable

class DetailsActivity : AppCompatActivity() {

    private lateinit var activityDetailsBinding: ActivityDetailsBinding

    private var propertyId: Long = 0

    private lateinit var mDetailsFragment: DetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = activityDetailsBinding.root
        val property = intent.getStringExtra("id")
        propertyId = property?.toLong()!!
        setContentView(view)
        setFragment()
    }

    private fun setFragment() {
        mDetailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putLong("id", propertyId)
        mDetailsFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_details, mDetailsFragment)
                .commit()
    }
}