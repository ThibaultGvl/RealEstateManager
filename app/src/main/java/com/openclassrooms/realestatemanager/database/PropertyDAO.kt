package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDAO {

    @Query("SELECT * FROM property")
    fun getProperty(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id = :id")
    fun getPropertyById(id: Long): LiveData<Property>

    @Insert
    fun insertProperty(property: Property)

    @Delete
    fun deleteProperty(property: Property)

    @Update
    fun updateProperty(property: Property)

}