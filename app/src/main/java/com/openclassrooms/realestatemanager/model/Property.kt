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
                    var photos: String,
                    @ColumnInfo(name = "agent")
                    var agent: String) {
    constructor() : this(0,"",0F,0F,0F,
            "","","","","",
            "","","")

    fun fromContentValues(values: ContentValues): Property {
        val property = Property()
        if (values.containsKey("id")) property.id = values.getAsLong("id")
        if (values.containsKey("type")) property.type = values.getAsString("type")
        if (values.containsKey("price")) property.price = values.getAsFloat("price")
        if (values.containsKey("surface")) property.surface = values.getAsFloat("surface")
        if (values.containsKey("piece")) property.piece = values.getAsFloat("piece")
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