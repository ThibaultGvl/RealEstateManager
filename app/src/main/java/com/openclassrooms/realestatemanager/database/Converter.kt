package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import java.util.*
import kotlin.collections.ArrayList

class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}