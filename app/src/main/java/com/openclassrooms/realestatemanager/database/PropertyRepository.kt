package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.model.Property

class PropertyRepository(var propertyDAO: PropertyDAO) {

    fun getPropertys(): LiveData<List<Property>> {
        return propertyDAO.getProperty()
    }

    fun getPropertyById(id: Long): LiveData<Property> {
        return propertyDAO.getPropertyById(id)
    }

    fun insertProperty(property: Property) {
        propertyDAO.insertProperty(property)
    }

    fun deleteProperty(propertyId: Long) {
        propertyDAO.deleteProperty(propertyId)
    }

    fun updateProperty(property: Property) {
        propertyDAO.updateProperty(property)
    }
}