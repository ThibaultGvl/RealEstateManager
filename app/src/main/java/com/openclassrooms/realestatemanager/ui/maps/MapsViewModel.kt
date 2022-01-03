package com.openclassrooms.realestatemanager.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import java.util.concurrent.Executor

class MapsViewModel(var propertyRepository: PropertyRepository, var executor: Executor): ViewModel() {

    var mPropertiesMutableLiveData: LiveData<List<Property>>? = null

    fun initProperties() {
        if (mPropertiesMutableLiveData != null) {
            return
        }
        mPropertiesMutableLiveData = propertyRepository.getPropertys()
    }

    fun getProperties(): LiveData<List<Property>>? {
        return mPropertiesMutableLiveData
    }
}