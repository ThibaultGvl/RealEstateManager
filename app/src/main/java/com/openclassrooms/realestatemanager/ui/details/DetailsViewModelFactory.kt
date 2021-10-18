package com.openclassrooms.realestatemanager.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.ui.property.ListPropertyViewModel
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(var propertyRepository: PropertyRepository, var executor: Executor):
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(propertyRepository, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}