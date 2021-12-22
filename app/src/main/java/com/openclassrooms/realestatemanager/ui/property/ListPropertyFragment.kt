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
import com.openclassrooms.realestatemanager.Injection.Injection

class ListPropertyFragment : Fragment(), OnItemClickListener {

    private lateinit var listPropertyViewModel: ListPropertyViewModel

    private var propertys: ArrayList<Property> = ArrayList()

    private lateinit var binding: FragmentListPropertyBinding

    private lateinit var recyclerView: RecyclerView

    private var mAdapter = ListPropertyAdapter(propertys, this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        binding = FragmentListPropertyBinding.inflate(inflater)
        recyclerView = binding.root
        recyclerView.adapter = mAdapter
        return recyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureViewModel()
        if (arguments != null) {
            createRequestFilter()
        }
        else {
            listPropertyViewModel.getPropertys()?.observe(this, this::updateProperties)
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
        if (filter.priceMin != 0.0.toFloat()) {
            queryString += if(argsNumber != 0) {
                " AND"
            } else {
                " WHERE"
            }
            val min = filter.priceMin
            queryString += " price > ${min.toDouble()}"
            argsNumber += 1
        }
        if (filter.priceMax != 0.0.toFloat()) {
            if(argsNumber != 0) {
                queryString += " AND"
            }
            val max = filter.priceMax
            queryString += " price < ${max.toDouble()}"
            argsNumber += 1
        }
        if (filter.surfaceMin != 0.0.toFloat()) {
            if(argsNumber != 0) {
                queryString += " AND"
            }
            val min = filter.surfaceMin
            queryString += " surface > ${min.toDouble()}"
            argsNumber += 1
        }
        if (filter.surfaceMax != 0.0.toFloat()) {
            if(argsNumber != 0) {
                queryString += " AND"
            }
            val max = filter.surfaceMax
            queryString += " surface < ${max.toDouble()}"
            argsNumber += 1
        }
        if (filter.interestPoint != " ") {
            if(argsNumber != 0) {
                queryString += " WHERE"
            }
            val interest = filter.interestPoint
            queryString += " interest_point LIKE '%$interest%'"
        }
        val query = SimpleSQLiteQuery(queryString)
        listPropertyViewModel.getFilterProperties(query).observe(this, this::updateProperties)
    }

    private fun updateProperties(databaseList: List<Property>) {
        propertys.clear()
        propertys.addAll(databaseList)
        mAdapter.notifyDataSetChanged()
    }

    private fun configureViewModel() {
        val injection = Injection::class.java
        val mViewModelFactory = injection.newInstance()
                .provideViewModelFactory(this.requireContext())
        listPropertyViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(ListPropertyViewModel::class.java)
        listPropertyViewModel.initPropertys()
    }

    override fun onItemClick(propertyId: Long) {
        (activity as MainActivity).onItemClick(propertyId)
    }
}