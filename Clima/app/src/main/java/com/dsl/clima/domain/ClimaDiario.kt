package com.dsl.clima.domain

import com.squareup.moshi.Json

data class ClimaDiario(@Json(name = "dt") val fecha: Int = 0,
                       @Json(name = "temp") val temperatura: Temperatura = Temperatura(),
                       @Json(name = "weather") val clima: List<Clima> = listOf(
                           Clima()
                       ))