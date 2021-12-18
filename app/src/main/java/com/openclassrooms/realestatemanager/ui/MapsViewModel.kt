package com.openclassrooms.realestatemanager.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import java.util.concurrent.Executor

class MapsViewModel(var propertyRepository: PropertyRepository, var executor: Executor): ViewModel() {

    var propertiesMutableLiveData: LiveData<List<Property>>? = null

    fun initProperties() {
        if (propertiesMutableLiveData != null) {
            return
        }
        propertiesMutableLiveData = propertyRepository.getPropertys()
    }

    fun getProperties(): LiveData<List<Property>>? {
        return propertiesMutableLiveData
    }
}