package com.openclassrooms.realestatemanager.model

import androidx.room.*
import com.openclassrooms.realestatemanager.database.Converter
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "property")
data class Property(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id")
                    val id: Long,
                    @ColumnInfo(name = "type")
                    var type: String,
                    @ColumnInfo(name = "price")
                    var price: Float,
                    @ColumnInfo(name = "surface")
                    var surface: Float,
                    @ColumnInfo(name = "piece")
                    var piece: Float,
                    @ColumnInfo(name = "description")
                    var description: String,
                    @ColumnInfo(name = "address")
                    var address: String,
                    @ColumnInfo(name = "interest_point")
                    var interestPoint: String,
                    @ColumnInfo(name = "status")
                    var status: String,
                    @ColumnInfo(name = "creation_date")
                    var creationDate: String,
                    @ColumnInfo(name = "sell_date")
                    var sellDate: String,
                    @ColumnInfo(name = "photos")
                    @TypeConverters(Converter::class)
                    var photos: List<String>,
                    @ColumnInfo(name = "agent")
                    var agent: String)