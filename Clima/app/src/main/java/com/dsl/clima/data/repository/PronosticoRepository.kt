package com.dsl.clima.data.repository

import com.dsl.clima.data.source.remote.CiudadRemote
import com.dsl.clima.data.source.remote.PronosticoDiarioRemote
import com.dsl.clima.data.source.remote.PronosticoRemote
import com.dsl.clima.data.source.remote.apiService
import com.dsl.clima.domain.model.CiudadModel
import com.dsl.clima.domain.model.PronosticoActualModel
import com.dsl.clima.domain.model.PronosticoDiarioModel
import com.dsl.clima.domain.model.PronosticoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PronosticoRepository(
    //private val dataLocal: PronosticoDatabaseDao
) {
    private lateinit var pronosticoModel: PronosticoModel

    suspend fun refrescarPronostico(nombreCiudad: String): PronosticoModel {
        withContext(Dispatchers.IO) {
            val ciudadRemote = apiService.retrofitService.getCiudad(nombreCiudad).await()
            val pronosticoRemote = apiService.retrofitService.getPronostico(ciudadRemote.coordenadaCiudad.latitud, ciudadRemote.coordenadaCiudad.longitud).await()
            pronosticoModel = transformarPronosticoRemoteModel(ciudadRemote, pronosticoRemote)
        }
        return pronosticoModel
    }
}

fun transformarPronosticoRemoteModel(ciudadRemote: CiudadRemote, pronosticoRemote: PronosticoRemote) : PronosticoModel {
    return PronosticoModel(
        CiudadModel(
            ciudadRemote.nombreCiudad,
            ciudadRemote.sys.nombrePais),
        PronosticoActualModel(
            pronosticoRemote.pronosticoActual.fechaActual,
            pronosticoRemote.pronosticoActual.tempActual,
            pronosticoRemote.pronosticoActual.nubosidadActual,
            pronosticoRemote.pronosticoActual.velocidadVientoActual,
            pronosticoRemote.pronosticoActual.humedadActual,
            pronosticoRemote.pronosticoActual.climaActual[0].descripcion,
            pronosticoRemote.pronosticoActual.climaActual[0].icono),
        transformarListaPronosticoDiarioRemoteModel(pronosticoRemote.pronosticoDiario)
    )
}

fun transformarListaPronosticoDiarioRemoteModel(listaPronosticoDiarioRemote: List<PronosticoDiarioRemote>) : List<PronosticoDiarioModel> {
    return listaPronosticoDiarioRemote.map {
        PronosticoDiarioModel(
            fechaDiaria = it.fechaDiaria,
            temperaturaMin = it.temperaturaDiaria.tempMin,
            temperaturaMax = it.temperaturaDiaria.tempMax,
            descripcionDiaria = it.climaDiaria[0].descripcion,
            iconoDiario = it.climaDiaria[0].icono
        )
    }
}