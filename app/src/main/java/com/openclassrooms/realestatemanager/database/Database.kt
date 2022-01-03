package com.openclassrooms.realestatemanager.database

import android.content.ContentValues
import android.content.Context
import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.model.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {

    abstract val propertyDao: PropertyDAO

    companion object {

        @Volatile
        private var mInstance: com.openclassrooms.realestatemanager.database.Database? = null

        fun getInstance(context: Context): com.openclassrooms.realestatemanager.database.Database {
            synchronized(this) {

                var instance = mInstance

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            com.openclassrooms.realestatemanager.database.Database::class.java,
                            "Db").fallbackToDestructiveMigration()
                            .addCallback(prepopulateDataBase())
                            .allowMainThreadQueries()
                            .build()
                    mInstance = instance
                }
                return instance
            }
        }
        private fun prepopulateDataBase(): Callback {

            return object : Callback() {
                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val loft = ContentValues()
                    loft.put("type", "Loft")
                    loft.put("price", 1499999)
                    loft.put("surface", 125)
                    loft.put("piece", 2)
                    loft.put("description", "A beautiful loft with all you need to be happy, " +
                            "this new yorker style estate is really atypical and he will answer " +
                            "to all your needed")
                    loft.put("address", "12 rue du clerc")
                    loft.put("status", "For Sale")
                    loft.put("interest_point", "School, Restaurant")
                    loft.put("creation_date", "26/06/2019")
                    loft.put("sell_date", "12/10/2020")
                    loft.put("photos", "")
                    loft.put("agent", "Thibault")
                    db.insert("property", OnConflictStrategy.IGNORE, loft)
                    val house = ContentValues()
                    house.put("type", "House")
                    house.put("price", 5400000)
                    house.put("surface", 205)
                    house.put("piece", 5)
                    house.put("description", "A charming house in a very calm village with " +
                            "old fashion style. He's also near from all, it will be very easy to" +
                            " live here every days")
                    house.put("address", "Albi")
                    house.put("status", "For Sale")
                    house.put("interest_point", "Cinema, Bakery")
                    house.put("creation_date", "27/07/2020")
                    house.put("sell_date", "13/11/2020")
                    house.put("photos", "")
                    house.put("agent", "Thibault")
                    db.insert("property", OnConflictStrategy.IGNORE, house)
                }
            }
        }
    }

}