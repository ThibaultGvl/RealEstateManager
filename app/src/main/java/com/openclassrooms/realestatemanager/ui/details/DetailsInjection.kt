package com.openclassrooms.realestatemanager.ui.details

import android.content.Context
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.ui.property.ListPropertyViewModelFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DetailsInjection {
    fun providePropertyDataSource(context: Context): PropertyRepository {
        val mDataBase = Database.getInstance(context)
        return PropertyRepository(mDataBase.propertyDao)
    }

    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): DetailsViewModelFactory {
        val propertyRepository = providePropertyDataSource(context)
        val executor = provideExecutor()
        return DetailsViewModelFactory(propertyRepository, executor)
    }
}