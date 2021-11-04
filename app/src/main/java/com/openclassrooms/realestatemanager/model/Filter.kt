package com.openclassrooms.realestatemanager.model

import java.io.Serializable

data class Filter(var type: String, var priceMin: Float, var priceMax: Float, var surfaceMin: Float,
                  var surfaceMax: Float, var interestPoint: String): Serializable