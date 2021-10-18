package com.openclassrooms.realestatemanager.ui.property

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.OnItemClickListener
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertyBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.MainActivity

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
        listPropertyViewModel.getPropertys()?.observe(this, this::updateProperties)
    }

    private fun updateProperties(databaseList: List<Property>) {
        propertys.clear()
        propertys.addAll(databaseList)
        mAdapter.notifyDataSetChanged()
    }

    fun search(searchView: SearchView) {
        for (property: Property in propertys) {
            if (searchView.queryHint?.let { property.type.contains(it) } == true) run {
                val propertiesSort = ArrayList<Property>()
                propertiesSort.add(property)
            }
        }
    }

    private fun configureViewModel() {
        val listPropertyInjection = ListPropertyInjection::class.java
        val mViewModelFactory = listPropertyInjection.newInstance()
                .provideViewModelFactory(this.requireContext())
        listPropertyViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(ListPropertyViewModel::class.java)
        listPropertyViewModel.initPropertys()
    }

    override fun onItemClick(propertyId: Long) {
        (activity as MainActivity).onItemClick(propertyId)
    }
}