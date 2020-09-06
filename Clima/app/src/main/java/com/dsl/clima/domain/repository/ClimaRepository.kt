package com.dsl.clima.domain.repository

import androidx.lifecycle.MutableLiveData
import com.dsl.clima.data.model.DatosMeteorologicosActuales
import com.dsl.clima.data.model.DatosMeteorologicosActualesPrevistos
import com.dsl.clima.data.source.local.ClimaCache
import com.dsl.clima.data.source.local.ClimaDatabaseDao
import com.dsl.clima.data.source.remote.ClimaService
import com.dsl.clima.data.source.remote.climaService
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClimaRepository(//private val dataRemote: ClimaService,
                      private val dataLocal: ClimaDatabaseDao) {

    suspend fun refrescarClima(ciudad: String) {
        withContext(Dispatchers.IO) {
            val getDatosMeteorologicosActualesDeferred = climaService.retrofitService.getDatosMeteorologicosActuales(ciudad)
            try {
                //_estadoApi.value = EstadoApi.CARGANDO
                val result_1 = getDatosMeteorologicosActualesDeferred.await()
                val getDatosMeteorologicosActualesPrevistosDeferred = climaService.retrofitService.getDatosMeteorologicosActualesPrevistos(
                    result_1.coordenadaCiudad.latitud, result_1.coordenadaCiudad.longitud)
                val result_2 = getDatosMeteorologicosActualesPrevistosDeferred.await()
                //_estadoApi.value = EstadoApi.FINALIZADO
            } catch (e: Exception) {
                //_estadoApi.value = EstadoApi.ERROR
            }
        }
    }
}