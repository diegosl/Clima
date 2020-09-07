package com.dsl.clima.domain.repository

import com.dsl.clima.data.source.local.ClimaCache
import com.dsl.clima.data.source.local.ClimaDatabaseDao
import com.dsl.clima.data.source.remote.climaService
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClimaRepository(private val dataLocal: ClimaDatabaseDao) {

    var estadoApi = EstadoApi.CARGANDO
    private var cache = ClimaCache()

    val time = 1

    suspend fun refrescarClima(ciudad: String): ClimaCache {
        withContext(Dispatchers.IO) {
            if(dataLocal.getClimaCache()==null) {
                cache = ClimaCache()
                dataLocal.insertarClima(cache)
            }
            if(time>0) {
                getDatosRemoto(ciudad)
            }
            else {
                estadoApi = EstadoApi.CARGANDO
                cache = dataLocal.getClimaCache()!!
                estadoApi = EstadoApi.FINALIZADO
            }
        }
        return cache
    }

    private suspend fun getDatosRemoto(ciudad: String) {
        withContext(Dispatchers.IO) {
            val getDatosMeteorologicosActualesDeferred = climaService.retrofitService.getDatosMeteorologicosActuales(ciudad)
            try {
                estadoApi = EstadoApi.CARGANDO
                val result_1 = getDatosMeteorologicosActualesDeferred.await()
                val getDatosMeteorologicosActualesPrevistosDeferred = climaService.retrofitService.getDatosMeteorologicosActualesPrevistos(
                    result_1.coordenadaCiudad.latitud, result_1.coordenadaCiudad.longitud)
                val result_2 = getDatosMeteorologicosActualesPrevistosDeferred.await()

                cache = ClimaCache(
                    nombreCiudad = result_1.ciudad,
                    nombrePais = result_1.sys.pais,
                    climaActual = result_2.climaActual,
                    climaDiario = result_2.climaDiario
                )

                dataLocal.actualizarClima(cache)
                estadoApi = EstadoApi.FINALIZADO

            } catch (e: Exception) {
                estadoApi = EstadoApi.ERROR
            }
        }
    }
}