package com.dsl.clima.data

import com.squareup.moshi.Json

data class Coordenada(@Json(name = "lon") val longitud: Double = 0.0,
                      @Json(name = "lat") val latitud: Double = 0.0)