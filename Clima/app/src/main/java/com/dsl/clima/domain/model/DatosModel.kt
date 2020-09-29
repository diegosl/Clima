package com.dsl.clima.domain.model

data class PronosticoModel(val ciudadModel: CiudadModel = CiudadModel(),
                           val pronosticoActualModel: PronosticoActualModel = PronosticoActualModel(),
                           val listaPronosticoDiarioModel: List<PronosticoDiarioModel> = listOf(PronosticoDiarioModel()))

data class CiudadModel(val idCiudad: Int = -1,
                       val nombreCiudad: String = "--",
                       val nombrePais: String = "--")

data class PronosticoActualModel(val fechaActual: Int = 0,
                                 val temperaturaActual: Double = 0.0,
                                 val nubosidadActual: Double = 0.0,
                                 val velocidadVientoActual: Double = 0.0,
                                 val humedadActual: Double = 0.0,
                                 val descripcionActual: String = "--",
                                 val iconoActual: String = "")

data class PronosticoDiarioModel(val fechaDiaria: Int = 0,
                                 val temperaturaMin: Double = 0.0,
                                 val temperaturaMax: Double = 0.0,
                                 val descripcionDiaria: String = "--",
                                 val iconoDiario: String = "")