package com.openclassrooms.realestatemanager.ui.insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.PropertyRepository
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

@Suppress("UNCHECKED_CAST")
class InsertViewModelFactory(var propertyRepository: PropertyRepository, var executor: Executor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InsertViewModel::class.java)) {
            return InsertViewModel(propertyRepository, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}