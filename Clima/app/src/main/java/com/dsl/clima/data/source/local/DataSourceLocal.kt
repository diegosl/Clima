package com.dsl.clima.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_pronostico_local")
data class PronosticoLocal(@PrimaryKey
                           @ColumnInfo(name = "nombre_ciudad")
                           val nombreCiudad: String = "",
                           @ColumnInfo(name = "nombre_pais")
                           val nombrePais: String = "",
                           @ColumnInfo(name = "fecha_actual")
                           val fechaActual: Int = 0,
                           @ColumnInfo(name = "temperatura_actual")
                           val tempActual: Double = 0.0,
                           @ColumnInfo(name = "humedad_actual")
                           val humedadActual: Double = 0.0,
                           @ColumnInfo(name = "nubosidad_actual")
                           val nubosidadActual: Double = 0.0,
                           @ColumnInfo(name = "velocidad_viento_actual")
                           val velocidadVientoActual: Double = 0.0,
                           @ColumnInfo(name = "descripcion_actual")
                           val descripcionActual: String = "",
                           @ColumnInfo(name = "icono_actual")
                           val iconoActual: String = "")