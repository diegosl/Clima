package com.dsl.clima.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClimaDatabaseDao {
    @Insert
    fun insertarClima(clima: ClimaCache)

    @Update
    fun actualizarClima(clima: ClimaCache)

    @Query("DELETE FROM tabla_clima_cache")
    fun eliminarClima()

    @Query("SELECT * FROM tabla_clima_cache LIMIT 1")
    fun getClimaCache(): ClimaCache?
}