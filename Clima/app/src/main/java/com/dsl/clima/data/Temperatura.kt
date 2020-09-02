package com.dsl.clima.data

import com.squareup.moshi.Json

data class Temperatura(@Json(name = "min") val tempMin: Double = 0.0,
                       @Json(name = "max") val tempMax: Double = 0.0)