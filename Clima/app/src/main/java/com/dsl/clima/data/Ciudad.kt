package com.dsl.clima.data

import com.squareup.moshi.Json

data class Ciudad(@Json(name = "id") val id: Int = -1,
                  @Json(name = "weather") val clima: List<Clima> = listOf(Clima()),
                  @Json(name = "name") val nombre: String = "",
                  @Json(name = "sys") val sys: Sys = Sys(),
                  @Json(name = "dt") val fecha: Int = 0,
                  @Json(name = "clouds") val nubosidad: Nubosidad = Nubosidad(),
                  @Json(name = "wind") val viento: Viento = Viento(),
                  @Json(name = "main") val pronostico: Pronostico = Pronostico())