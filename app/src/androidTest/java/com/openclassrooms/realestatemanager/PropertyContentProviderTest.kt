package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.provider.PropertyContentProvider
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class PropertyContentProviderTest {

    private lateinit var mContentResolver: ContentResolver

    private val PROPERTY_ID:Long = 1

    private val NO_PROPERTY_ID: Long = 15

    @Before
    fun setUp(){

        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,Database::class.java)
                .allowMainThreadQueries()
                .build()
        mContentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun getItemsWhenNoItemInserted(){
        val cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider().URI_ITEM, NO_PROPERTY_ID),null,null,null,null)
        assertNotNull(cursor)
        assertEquals(0, cursor?.count)
        cursor?.close()
    }

    @Test
    fun insertAndGetItem(){
        mContentResolver.insert(PropertyContentProvider().URI_ITEM, generateEstate())

        val cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider().URI_ITEM, PROPERTY_ID), null,null,null,null)
        assertNotNull(cursor)
        assertEquals(1,cursor?.count)
        assertEquals(true,cursor?.moveToFirst())
        assertEquals("Loft",cursor?.getString(cursor.getColumnIndexOrThrow("type")))
    }


    private fun generateEstate():ContentValues{
        val values = ContentValues()
        values.put("type","Loft")
        values.put("price",200000)
        values.put("surface",200)
        values.put("piece","6")
        values.put("description","property")
        values.put("address","18 boulevard Carnot")
        values.put("interest_point","school")
        values.put("photos", "")
        values.put("status","Sold")
        values.put("entryDate", "")
        values.put("date of sale","")
        values.put("agent","Thibault")
        return values
    }
}