package com.openclassrooms.realestatemanager.model

import java.io.Serializable

data class Filter(var type: String, var priceMin: Int, var priceMax: Int, var surfaceMin: Int,
                  var surfaceMax: Int, var interestPoint: String): Serializable