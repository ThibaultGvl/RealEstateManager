package com.openclassrooms.realestatemanager.ui.insert

import android.content.Context
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.ui.details.DetailsViewModelFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class InsertInjection {
    fun providePropertyDataSource(context: Context): PropertyRepository {
        val mDataBase = Database.getInstance(context)
        return PropertyRepository(mDataBase.propertyDao)
    }

    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): InsertViewModelFactory {
        val propertyRepository = providePropertyDataSource(context)
        val executor = provideExecutor()
        return InsertViewModelFactory(propertyRepository, executor)
    }
}