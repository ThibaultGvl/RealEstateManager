package com.openclassrooms.realestatemanager.ui.property

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import java.util.concurrent.Executor

class ListPropertyViewModel(var repository: PropertyRepository) : ViewModel() {

    var propertysMutableLiveData: LiveData<List<Property>>? = null

    fun initPropertys() {
        if (propertysMutableLiveData != null) {
            return
        }
        propertysMutableLiveData = repository.getPropertys()
    }

    fun getPropertys(): LiveData<List<Property>>? {
        return propertysMutableLiveData
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