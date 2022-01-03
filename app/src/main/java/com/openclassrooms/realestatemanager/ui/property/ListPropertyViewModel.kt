package com.openclassrooms.realestatemanager.ui.property

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property

class ListPropertyViewModel(var repository: PropertyRepository) : ViewModel() {

    var mPropertiesMutableLiveData: LiveData<List<Property>>? = null

    fun initPropertys() {
        if (mPropertiesMutableLiveData != null) {
            return
        }
        mPropertiesMutableLiveData = repository.getPropertys()
    }

    fun getPropertys(): LiveData<List<Property>>? {
        return mPropertiesMutableLiveData
    }

    fun getFilterProperties(query: SupportSQLiteQuery): LiveData<List<Property>> {
        return repository.getPropertyByFilter(query)
    }

    fun insertProperty(property: Property) {
        repository.insertProperty(property)
    }

    fun deleteProperty(propertyId: Long) {
        repository.deleteProperty(propertyId)
    }

    fun updateProperty(property: Property) {
        repository.updateProperty(property)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}