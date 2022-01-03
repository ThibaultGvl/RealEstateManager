package com.openclassrooms.realestatemanager.ui.property

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.OnItemClickListener
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertyBinding
import com.openclassrooms.realestatemanager.model.Filter
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.MainActivity
import com.openclassrooms.realestatemanager.injection.Injection

class ListPropertyFragment : Fragment(), OnItemClickListener {

    private lateinit var mListPropertyViewModel: ListPropertyViewModel

    private var mProperties: ArrayList<Property> = ArrayList()

    private lateinit var mBinding: FragmentListPropertyBinding

    private lateinit var mRecyclerView: RecyclerView

    private var mAdapter = ListPropertyAdapter(mProperties, this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        mBinding = FragmentListPropertyBinding.inflate(inflater)
        mRecyclerView = mBinding.root
        mRecyclerView.adapter = mAdapter
        return mRecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureViewModel()
        if (arguments != null) {
            createRequestFilter()
        }
        else {
            mListPropertyViewModel.getPropertys()?.observe(this, this::updateProperties)
        }
    }

    private fun createRequestFilter() {
        val filter = requireArguments().getSerializable("filter") as Filter
        var queryString = "SELECT * FROM property"
        var argsNumber = 0
        if (filter.type != " ") {
            val type = filter.type
            queryString += " WHERE type = '$type'"
            argsNumber += 1
        }
        if (filter.priceMin != 0) {
            queryString += if(argsNumber != 0) {
                " AND"
            } else {
                " WHERE"
            }
            val min = filter.priceMin
            queryString += " price > $min"
            argsNumber += 1
        }
        if (filter.priceMax != 0) {
            queryString += if(argsNumber != 0) {
                " AND"
            } else {
                " WHERE"
            }
            val max = filter.priceMax
            queryString += " price < $max"
            argsNumber += 1
        }
        if (filter.surfaceMin != 0) {
            queryString += if(argsNumber != 0) {
                " AND"
            } else {
                " WHERE"
            }
            val min = filter.surfaceMin
            queryString += " surface > $min"
            argsNumber += 1
        }
        if (filter.surfaceMax != 0) {
            queryString += if(argsNumber != 0) {
                " AND"
            }else {
                " WHERE"
            }
            val max = filter.surfaceMax
            queryString += " surface < $max"
            argsNumber += 1
        }
        if (filter.interestPoint != " ") {
            queryString += if(argsNumber == 0) {
                " WHERE"
            } else {
                " AND"
            }
            val interest = filter.interestPoint
            queryString += " interest_point LIKE '%$interest%'"
        }
        val query = SimpleSQLiteQuery(queryString)
        mListPropertyViewModel.getFilterProperties(query).observe(this, this::updateProperties)
    }

    private fun updateProperties(databaseList: List<Property>) {
        mProperties.clear()
        mProperties.addAll(databaseList)
        mAdapter.notifyDataSetChanged()
    }

    private fun configureViewModel() {
        val injection = Injection::class.java
        val mViewModelFactory = injection.newInstance()
                .provideViewModelFactory(this.requireContext())
        mListPropertyViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(ListPropertyViewModel::class.java)
        mListPropertyViewModel.initPropertys()
    }

    override fun onItemClick(propertyId: Long) {
        (activity as MainActivity).onItemClick(propertyId)
    }
}