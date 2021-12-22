package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.openclassrooms.realestatemanager.database.Database
import com.openclassrooms.realestatemanager.model.Property


class PropertyContentProvider : ContentProvider() {
    companion object {
        val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        val TABLE_NAME = Property::class.java.simpleName
        val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        if (context != null) {
            val id:Long = ContentUris.parseId(uri)
            val cursor = Database.getInstance(context!!).propertyDao.getPropertiesWithCursor(id)
            cursor.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri : $uri");
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.property/$AUTHORITY.$TABLE_NAME";
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (context != null && values != null) {
            val id = Database.getInstance(context!!).propertyDao.insertProperty(Property().fromContentValues(values))
            context!!.contentResolver.notifyChange(uri, null)
            if (id != 0L) {
                context!!.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        if (context != null) {
            Database.getInstance(context!!).propertyDao.deleteProperty(ContentUris.parseId(uri))
            context!!.contentResolver.notifyChange(uri, null)
        }
        throw IllegalArgumentException("Failed to delete row into $uri");
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        if (context != null) {
            val count:Int = Database.getInstance(context!!).propertyDao.updateProperty(Property().fromContentValues(values!!))
            context!!.contentResolver.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }
}