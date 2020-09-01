package com.dsl.clima.data

import com.squareup.moshi.Json

data class Pronostico(@Json(name = "temp") val temp: Double = 0.0,
                      @Json(name = "temp_min") val tempMin: Double = 0.0,
                      @Json(name = "temp_max") val tempMax: Double = 0.0,
                      @Json(name = "pressure") val presion: Double = 0.0,
                      @Json(name = "humidity") val humedad: Double = 0.0)