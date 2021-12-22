package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.Database;
import com.openclassrooms.realestatemanager.provider.PropertyContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PropertyContentProviderTest {
    private ContentResolver mContentResolver;
    private final long ID = 4546;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), Database.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getPropertiesWhenNoPropertyInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.Companion.getURI_ITEM(), ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    @Test
    public void insertAndGetProperty() {
        mContentResolver.insert(PropertyContentProvider.Companion.getURI_ITEM(), generateItem());
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.Companion.getURI_ITEM(), ID), null, null, null, null);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals("Loft", cursor.getString(cursor.getColumnIndexOrThrow("type")));
    }

    private ContentValues generateItem(){
        ContentValues loft = new ContentValues();
        loft.put("type", "Loft");
        loft.put("price", 1499999);
        loft.put("surface", 125);
        loft.put("piece", 2);
        loft.put("description", "A beautiful loft in the center of Paris");
        loft.put("address", "Paris");
        loft.put("status", "For Sale");
        loft.put("interest_point", "School, Restaurant");
        loft.put("creation_date", "26/06/2019");
        loft.put("sell_date", "12/10/2020");
        loft.put("photos", "vfsv");
        loft.put("agent", "Thibault");
        return loft;
    }
}
