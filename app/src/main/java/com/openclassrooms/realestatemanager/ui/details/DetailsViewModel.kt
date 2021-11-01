package com.openclassrooms.realestatemanager.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import java.util.concurrent.Executor

class DetailsViewModel(var propertyRepository: PropertyRepository, executor: Executor) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Media"
    }

    fun getPropertyById(id: Long): LiveData<Property> {
        return propertyRepository.getPropertyById(id)
    }

    fun deleteProperty(propertyId: Long) {
        propertyRepository.deleteProperty(propertyId)
    }

    val text: LiveData<String> = _text
}