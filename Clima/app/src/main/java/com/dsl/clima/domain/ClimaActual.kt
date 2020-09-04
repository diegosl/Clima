package com.dsl.clima.domain

import com.dsl.clima.domain.Clima
import com.squareup.moshi.Json

data class ClimaActual(@Json(name = "dt") val fecha: Int = 0,
                       @Json(name = "temp") val tempActual: Double = 0.0,
                       @Json(name = "humidity") val humedad: Double = 0.0,
                       @Json(name = "clouds") val nubosidad: Double = 0.0,
                       @Json(name = "wind_speed") val velocidadViento: Double = 0.0,
                       @Json(name = "weather") val clima: List<Clima> = listOf(
                           Clima()
                       ))