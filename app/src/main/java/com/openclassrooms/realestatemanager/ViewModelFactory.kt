package com.openclassrooms.realestatemanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.ui.details.DetailsViewModel
import com.openclassrooms.realestatemanager.ui.maps.MapsViewModel
import com.openclassrooms.realestatemanager.ui.insert.InsertViewModel
import com.openclassrooms.realestatemanager.ui.property.ListPropertyViewModel
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(var propertyRepository: PropertyRepository, var executor: Executor):
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(propertyRepository, executor) as T
        }
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(propertyRepository, executor) as T
        }
        if (modelClass.isAssignableFrom(ListPropertyViewModel::class.java)){
            return ListPropertyViewModel(propertyRepository) as T
        }
        if (modelClass.isAssignableFrom(InsertViewModel::class.java)){
            return InsertViewModel(propertyRepository, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}