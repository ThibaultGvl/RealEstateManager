package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import androidx.room.*


@Entity(tableName = "property")
data class Property(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id")
                    var id: Long = 0,
                    @ColumnInfo(name = "type")
                    var type: String,
                    @ColumnInfo(name = "price")
                    var price: Int,
                    @ColumnInfo(name = "surface")
                    var surface: Int,
                    @ColumnInfo(name = "piece")
                    var piece: Int,
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
                    var photos: String,
                    @ColumnInfo(name = "agent")
                    var agent: String) {
    constructor() : this(0,"",0,0,0,
            "","","","","",
            "","","")

    fun fromContentValues(values: ContentValues): Property {
        val property = Property()
        if (values.containsKey("type")) property.type = values.getAsString("type")
        if (values.containsKey("price")) property.price = values.getAsInteger("price")
        if (values.containsKey("surface")) property.surface = values.getAsInteger("surface")
        if (values.containsKey("piece")) property.piece = values.getAsInteger("piece")
        if (values.containsKey("description")) property.description = values.getAsString("description")
        if (values.containsKey("address")) property.address = values.getAsString("address")
        if (values.containsKey("interest_point")) property.interestPoint = values.getAsString("interest_point")
        if (values.containsKey("status")) property.status = values.getAsString("status")
        if (values.containsKey("creation_date")) property.creationDate = values.getAsString("creation_date")
        if (values.containsKey("sell_date")) property.sellDate = values.getAsString("sell_date")
        if (values.containsKey("photos")) property.photos = values.getAsString("photos")
        if (values.containsKey("agent")) property.agent = values.getAsString("agent")
        return property
    }
}