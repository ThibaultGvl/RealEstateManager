package com.openclassrooms.realestatemanager.ui.property

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.database.PropertyDAO
import com.openclassrooms.realestatemanager.database.PropertyRepository
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

@Suppress("UNCHECKED_CAST")
class ListPropertyViewModelFactory(var propertyRepository: PropertyRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPropertyViewModel::class.java)) {
            return ListPropertyViewModel(propertyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}