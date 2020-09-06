package com.dsl.clima.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dsl.clima.data.model.ClimaActual
import com.dsl.clima.data.model.ClimaDiario

@Entity(tableName = "tabla_clima_cache")
data class ClimaCache(@PrimaryKey(autoGenerate = true)
                      var id: Long = 0L,
                      @ColumnInfo(name = "nombre_ciudad")
                      val nombreCiudad: String = "",
                      @ColumnInfo(name = "nombre_pais")
                      val nombrePais: String = "",
                      @ColumnInfo(name = "clima_actual")
                      val climaActual: ClimaActual = ClimaActual(),
                      @ColumnInfo(name = "lista_clima_diario")
                      val climaDiario: List<ClimaDiario> = listOf(ClimaDiario()))