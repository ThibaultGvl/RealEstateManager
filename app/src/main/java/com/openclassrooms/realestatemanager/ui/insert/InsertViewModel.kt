package com.openclassrooms.realestatemanager.ui.insert

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import java.util.concurrent.Executor

class InsertViewModel(var propertyRepository: PropertyRepository, var executor: Executor): ViewModel() {

    fun createProperty(property: Property) {
        executor.execute{propertyRepository.insertProperty(property)}
    }

    fun getPropertyById(id: Long): LiveData<Property> {
        return propertyRepository.getPropertyById(id)
    }

    fun getProperties(): LiveData<List<Property>> {
        return propertyRepository.getPropertys()
    }

    fun updateProperty(property: Property) {
        executor.execute { propertyRepository.updateProperty(property) }
    }
}