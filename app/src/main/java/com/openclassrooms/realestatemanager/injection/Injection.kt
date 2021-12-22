package com.openclassrooms.realestatemanager.injection

import android.content.Context
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.database.PropertyRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {
    fun providePropertyDataSource(context: Context): PropertyRepository {
        val mDataBase = Database.getInstance(context)
        return PropertyRepository(mDataBase.propertyDao)
    }

    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val propertyRepository = providePropertyDataSource(context)
        val executor = provideExecutor()
        return ViewModelFactory(propertyRepository, executor)
    }
}