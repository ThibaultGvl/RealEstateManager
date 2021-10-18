package com.openclassrooms.realestatemanager.ui.property

import android.content.Context
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.database.PropertyRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ListPropertyInjection {

    fun providePropertyDataSource(context: Context): PropertyRepository {
        val mDataBase = Database.getInstance(context)
        return PropertyRepository(mDataBase.propertyDao)
    }

    fun provideViewModelFactory(context: Context): ListPropertyViewModelFactory {
        val propertyRepository = providePropertyDataSource(context)
        return ListPropertyViewModelFactory(propertyRepository)
    }
}