package com.openclassrooms.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment

class FilterActivity : AppCompatActivity() {

    private lateinit var mFilterFragment: FilterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        setFragment()
    }

    private fun setFragment() {
        mFilterFragment = FilterFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.filter_container, mFilterFragment)
                .commit()
    }
}