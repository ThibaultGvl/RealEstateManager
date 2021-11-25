package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import java.util.*
import kotlin.collections.ArrayList

class Converter {
    @TypeConverter
    fun convertStringToPhoto(photos: String): ArrayList<String> {
        val photosString = photos.split("\\s*,\\s*")
        val photoArray: ArrayList<String> = ArrayList()
        for (photo in photosString) {
            photoArray.add(photo)
        }
        return photoArray
    }

    @TypeConverter
    fun convertPhotoToString(photo: ArrayList<String>): String {
        return photo.toString()
    }
}