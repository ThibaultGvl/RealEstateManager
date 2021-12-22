package com.openclassrooms.realestatemanager.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDAO {

    @Query("SELECT * FROM property")
    fun getProperty(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id = :id")
    fun getPropertiesWithCursor(id: Long): Cursor

    @Query("SELECT * FROM property WHERE id = :id")
    fun getPropertyById(id: Long): LiveData<Property>

    @RawQuery(observedEntities = [Property::class])
    fun getPropertyByFilter(query: SupportSQLiteQuery): LiveData<List<Property>>

    @Insert
    fun insertProperty(property: Property): Long

    @Query("DELETE FROM property WHERE id = :propertyId")
    fun deleteProperty(propertyId: Long)

    @Update
    fun updateProperty(property: Property): Int

}