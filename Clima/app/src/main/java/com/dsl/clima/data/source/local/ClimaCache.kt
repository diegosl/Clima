package com.dsl.clima.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tabla_clima_cache")
data class ClimaCache(@ColumnInfo(name = "nombre_ciudad")
                      val nombreCiudad: String = "",
                      @ColumnInfo(name = "nombre_pais")
                      val nombrePais: String = "",
                      @ColumnInfo(name = "fecha_actual")
                      val fecha: Int = 0,
                      @ColumnInfo(name = "temperatura_actual")
                      val tempActual: Double = 0.0,
                      @ColumnInfo(name = "humedad_actual")
                      val humedad: Double = 0.0,
                      @ColumnInfo(name = "nubosidad_actual")
                      val nubosidad: Double = 0.0,
                      @ColumnInfo(name = "velocidad_viento_actual")
                      val velocidadViento: Double = 0.0,
                      @ColumnInfo(name = "descripcion_actual")
                      val descripcion: String = "",
                      @ColumnInfo(name = "icono_actual")
                      val icono: String = "")