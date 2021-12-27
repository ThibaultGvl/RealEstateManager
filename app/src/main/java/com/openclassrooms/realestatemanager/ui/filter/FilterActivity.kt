package com.openclassrooms.realestatemanager.ui.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var mFilterFragment: FilterFragment

    private lateinit var mBinding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFilterBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        setFragment()
        val toolbar = mBinding.filterToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val returnButton = mBinding.returnFilter
        returnButton.setOnClickListener { onBackPressed() }
    }

    private fun setFragment() {
        mFilterFragment = FilterFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.filter_container, mFilterFragment)
                .commit()
    }
}