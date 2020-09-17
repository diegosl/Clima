package com.dsl.clima.data.source.local

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "tabla_pronostico_local")
data class PronosticoLocal(@PrimaryKey
                           @ColumnInfo(name = "id_ciudad")
                           val idCiudad: Int = -1,
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
                           val iconoActual: String = "",
                           @ColumnInfo(name = "lista_pronostico_diario")
                           val listaPronosticoDiarioLocal: List<PronosticoDiarioLocal> = listOf(PronosticoDiarioLocal()))

data class PronosticoDiarioLocal(val fechaDiaria: Int = 0,
                                 val temperaturaMin: Double = 0.0,
                                 val temperaturaMax: Double = 0.0,
                                 val iconoDiario: String = "")

class Converters {
    @TypeConverter
    fun listToJson(value: List<PronosticoDiarioLocal>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<PronosticoDiarioLocal>::class.java).toList()
}