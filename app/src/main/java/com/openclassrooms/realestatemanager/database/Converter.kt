package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import java.util.*
import kotlin.collections.ArrayList

class Converter {
    @TypeConverter
    fun convertStringToPhoto(photos: String): List<String> {
        val photosString = photos.split("\\s*,\\s*")
        return photosString
    }

    @TypeConverter
    fun convertPhotoToString(photo: List<String>): String {
        return photo.toString()
    }
}