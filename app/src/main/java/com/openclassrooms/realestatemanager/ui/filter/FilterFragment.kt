package com.openclassrooms.realestatemanager.ui.filter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentFilterBinding
import com.openclassrooms.realestatemanager.model.Filter
import com.openclassrooms.realestatemanager.ui.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilterFragment : Fragment() {
    private lateinit var mBinding: FragmentFilterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mBinding = FragmentFilterBinding.inflate(inflater)
        mBinding.buttonFilter.setOnClickListener {
            val type = if(mBinding.typeEdit.text.isNotEmpty()){mBinding.typeEdit.text.toString()}
            else {" "}
            val priceMin = if(mBinding.priceMin.text.isNotEmpty()){mBinding.priceMin.text.toString()
                    .toInt()}else{0}
            val priceMax = if(mBinding.priceMax.text.isNotEmpty()){mBinding.priceMax.text.toString()
                    .toInt()}else{0}
            val surfaceMin = if(mBinding.surfaceMin.text.isNotEmpty()){mBinding.surfaceMin.text
                    .toString().toInt()}else{0}
            val surfaceMax = if(mBinding.surfaceMax.text.isNotEmpty()){mBinding.surfaceMax.text
                    .toString().toInt()}else{0}
            val interestPointEdit = if(mBinding.interestPointEdit.text.isNotEmpty())
            {mBinding.interestPointEdit.text.toString()}else{" "}
            val filter = Filter(type,
                    priceMin,
                    priceMax,
                    surfaceMin,
                    surfaceMax,
                    interestPointEdit)
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("filter", filter)
            startActivity(intent)
        }
        return mBinding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
                FilterFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}