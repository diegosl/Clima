package com.dsl.clima.data.model

import com.squareup.moshi.Json

data class DatosMeteorologicosActualesPrevistos(@Json(name = "lat") val latitud: Double = 0.0,
                                                @Json(name = "lon") val longitud: Double = 0.0,
                                                @Json(name = "current") val climaActual: ClimaActual = ClimaActual(),
                                                @Json(name = "daily") val climaDiario: List<ClimaDiario> = listOf(
                                                    ClimaDiario()
                                                ))