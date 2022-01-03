package com.openclassrooms.realestatemanager

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.model.Property
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var mDatabase: Database
    private var mProperty = Property()

    @Before
    @Throws(Exception::class)
    fun initDataBase() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation()
                .context,Database::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDatabase(){
        mDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getProperties() {
        val properties = this.mDatabase.propertyDao.getProperty().value
        if (properties != null) {
            //Database is prepopulate with 2 property
            assertEquals(2, properties.size)
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertProperty() {
        this.mDatabase.propertyDao.insertProperty(mProperty)
        val properties = this.mDatabase.propertyDao.getProperty().value
        if (properties != null) {
            assertEquals(3, properties.size)
        }
    }

    @Test
    fun updateProperty() {
        mProperty = Property(mProperty.id, "I have been updated with success",0
                ,0,0,"","","","","",
                "","","")
        this.mDatabase.propertyDao.updateProperty(mProperty)
        assertEquals("I have been updated with success", mProperty.type)
    }

    @Test
    fun deleteProperty() {
        this.mDatabase.propertyDao.deleteProperty(mProperty.id)
        val properties = this.mDatabase.propertyDao.getProperty().value
        if (properties != null) {
            assertEquals(3, properties.size)
        }
    }
}