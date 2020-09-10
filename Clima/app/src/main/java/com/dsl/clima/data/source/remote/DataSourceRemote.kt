package com.dsl.clima.data.source.remote

import com.squareup.moshi.Json

data class ClimaRemote(@Json(name = "description") val descripcion: String = "",
                       @Json(name = "icon") val icono: String = "")

data class PronosticoActualRemote(@Json(name = "dt") val fechaActual: Int = 0,
                                  @Json(name = "temp") val tempActual: Double = 0.0,
                                  @Json(name = "humidity") val humedadActual: Double = 0.0,
                                  @Json(name = "clouds") val nubosidadActual: Double = 0.0,
                                  @Json(name = "wind_speed") val velocidadVientoActual: Double = 0.0,
                                  @Json(name = "weather") val climaActual: List<ClimaRemote> = listOf(ClimaRemote()))

data class PronosticoDiarioRemote(@Json(name = "dt") val fechaDiaria: Int = 0,
                                  @Json(name = "temp") val temperaturaDiaria: TemperaturaRemote = TemperaturaRemote(),
                                  @Json(name = "weather") val climaDiaria: List<ClimaRemote> = listOf(ClimaRemote()))

data class CoordenadaRemote(@Json(name = "lon") val longitud: Double = 0.0,
                            @Json(name = "lat") val latitud: Double = 0.0)

data class TemperaturaRemote(@Json(name = "min") val tempMin: Double = 0.0,
                             @Json(name = "max") val tempMax: Double = 0.0)

data class SysRemote(@Json(name = "country") val nombrePais: String = "")

data class CiudadRemote(@Json(name = "id") val id: Int = -1,
                        @Json(name = "name") val nombreCiudad: String = "",
                        @Json(name = "coord") val coordenadaCiudad: CoordenadaRemote = CoordenadaRemote(),
                        @Json(name = "sys") val sys: SysRemote = SysRemote()
)

data class PronosticoRemote(@Json(name = "lat") val latitud: Double = 0.0,
                            @Json(name = "lon") val longitud: Double = 0.0,
                            @Json(name = "current") val pronosticoActual: PronosticoActualRemote = PronosticoActualRemote(),
                            @Json(name = "daily") val pronosticoDiario: List<PronosticoDiarioRemote> = listOf(PronosticoDiarioRemote()))