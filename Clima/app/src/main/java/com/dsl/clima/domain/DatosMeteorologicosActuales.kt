package com.dsl.clima.domain

import com.squareup.moshi.Json

data class DatosMeteorologicosActuales(@Json(name = "id") val id: Int = -1,
                                       @Json(name = "name") val ciudad: String = "",
                                       @Json(name = "coord") val coordenadaCiudad: Coordenada = Coordenada(),
                                       @Json(name = "sys") val sys: Sys = Sys()
)