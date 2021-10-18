package com.openclassrooms.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment
import com.openclassrooms.realestatemanager.ui.insert.InsertActivity
import com.openclassrooms.realestatemanager.ui.property.ListPropertyFragment
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mMainActivityBinding: ActivityMainBinding

    private lateinit var mListPropertyFragment: ListPropertyFragment

    private lateinit var mDetailsFragment: DetailsFragment

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mSearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mMainActivityBinding.root
        configureToolBar()
        showFragment()
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        configureToolBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun onSearch(menuItem: MenuItem) {
        val searchView = menuItem as SearchView
        mListPropertyFragment.search(searchView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add -> startIntentActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun startIntentActivity() {
        startActivity(Intent(this, InsertActivity::class.java))
    }

    private fun configureToolBar() {
        mDrawerLayout = mMainActivityBinding.drawerLayout
        val toolbar: Toolbar = mMainActivityBinding.toolbar
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.main)
        val toggle = ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        Objects.requireNonNull(supportActionBar)!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
    }

    private fun showFragment() {
            mListPropertyFragment = ListPropertyFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mListPropertyFragment)
                    .commit()

        if (mMainActivityBinding.secondFragmentContainer != null) {
            mDetailsFragment = DetailsFragment()

            supportFragmentManager.beginTransaction()
                        .replace(R.id.second_fragment_container, mDetailsFragment)
                        .commit()
            }
    }

    fun onItemClick(propertyId: Long) {
        if (mMainActivityBinding.secondFragmentContainer != null) {
            mDetailsFragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putLong("id", propertyId)
            mDetailsFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                    .replace(R.id.second_fragment_container, mDetailsFragment)
                    .commit()
        }
        else {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", propertyId.toString())
            startActivity(intent)
        }
    }
}
