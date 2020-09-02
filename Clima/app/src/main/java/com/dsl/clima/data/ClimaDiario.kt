package com.dsl.clima.data

import com.squareup.moshi.Json

data class ClimaDiario(@Json(name = "temp") val temperatura: Temperatura = Temperatura(),
                       @Json(name = "weather") val clima: List<Clima> = listOf(Clima()))