package com.dsl.clima.data.source.local

import androidx.room.*

@Dao
interface PronosticoDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarPronosticoLocal(pronosticoLocal: PronosticoLocal)

    @Update
    fun actualizarPronosticoLocal(pronosticoLocal: PronosticoLocal)

    @Query("DELETE FROM tabla_pronostico_local WHERE id_ciudad =:idCiudad")
    fun eliminarPronosticoLocal(idCiudad: Int)

    @Query("SELECT * FROM tabla_pronostico_local WHERE id_ciudad =:idCiudad LIMIT 1")
    fun getPronosticoLocal(idCiudad: Int): PronosticoLocal

    @Query("SELECT * FROM tabla_pronostico_local ORDER BY nombre_ciudad")
    fun getListaPronosticoLocal(): List<PronosticoLocal>
}