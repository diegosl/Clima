package com.dsl.clima.data.source.local

import androidx.room.*

@Dao
interface PronosticoDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarPronosticoLocal(pronosticoLocal: PronosticoLocal)

    @Update
    fun actualizarPronosticoLocal(pronosticoLocal: PronosticoLocal)

    @Query("DELETE FROM tabla_pronostico_local WHERE nombre_ciudad =:nombreCiudad")
    fun eliminarPronosticoLocal(nombreCiudad: String)

    @Query("SELECT * FROM tabla_pronostico_local WHERE nombre_ciudad =:nombreCiudad LIMIT 1")
    fun getPronosticoLocal(nombreCiudad: String): PronosticoLocal

    @Query("SELECT * FROM tabla_pronostico_local ORDER BY nombre_ciudad")
    fun getListaPronosticoLocal(): List<PronosticoLocal>
}