package com.dsl.clima.data.repository

import com.dsl.clima.data.source.local.PronosticoDatabaseDao
import com.dsl.clima.data.source.local.PronosticoLocal
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

class PronosticoRepository(private val dataLocal: PronosticoDatabaseDao) {
    suspend fun refrescarPronostico(nombreCiudad: String): PronosticoModel {
        var pronosticoModel = PronosticoModel()
        withContext(Dispatchers.IO) {
            var pronosticoLocal = dataLocal.getPronosticoSeleccionadoLocal(nombreCiudad)
            val ciudadRemote = apiService.retrofitService.getCiudad(pronosticoLocal.nombreCiudad).await()
            val pronosticoRemote = apiService.retrofitService.getPronostico(ciudadRemote.coordenadaCiudad.latitud, ciudadRemote.coordenadaCiudad.longitud).await()
            pronosticoModel = transformarPronosticoRemoteModel(ciudadRemote, pronosticoRemote)
            pronosticoLocal = transformarPronosticoModelLocal(pronosticoModel)
            dataLocal.actualizarPronosticoLocal(pronosticoLocal)
        }
        return pronosticoModel
    }

    suspend fun getCiudad(nombreCiudad: String): CiudadModel {
        var ciudadModel = CiudadModel()
        withContext(Dispatchers.IO) {
            val ciudadRemote = apiService.retrofitService.getCiudad(nombreCiudad).await()
            ciudadModel = transformarCiudadRemoteModel(ciudadRemote)
        }
        return ciudadModel
    }

    suspend fun insertarPronostico(pronosticoModel: PronosticoModel) {
        withContext(Dispatchers.IO) {
            val pronosticoLocal = transformarPronosticoModelLocal(pronosticoModel)
            dataLocal.insertarPronosticoLocal(pronosticoLocal)
        }
    }

    suspend fun getListaPronostico(): List<PronosticoModel> {
        var listaPronosticoModel = listOf(PronosticoModel())
        withContext(Dispatchers.IO) {
            listaPronosticoModel = transformarListaPronosticoLocalModel(dataLocal.getListaPronosticoLocal())
        }
        return listaPronosticoModel
    }

    suspend fun eliminarPronostico(nombreCiudad: String) {
        withContext(Dispatchers.IO) {
            dataLocal.eliminarPronosticoLocal(nombreCiudad)
        }
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

fun transformarCiudadRemoteModel(ciudadRemote: CiudadRemote) = CiudadModel(ciudadRemote.nombreCiudad, ciudadRemote.sys.nombrePais)

fun transformarPronosticoModelLocal(pronosticoModel: PronosticoModel) : PronosticoLocal {
    return PronosticoLocal(
        pronosticoModel.ciudadModel.nombreCiudad,
        pronosticoModel.ciudadModel.nombrePais,
        pronosticoModel.pronosticoActualModel.fechaActual,
        pronosticoModel.pronosticoActualModel.temperaturaActual,
        pronosticoModel.pronosticoActualModel.humedadActual,
        pronosticoModel.pronosticoActualModel.nubosidadActual,
        pronosticoModel.pronosticoActualModel.velocidadVientoActual,
        pronosticoModel.pronosticoActualModel.descripcionActual,
        pronosticoModel.pronosticoActualModel.iconoActual
    )
}

fun transformarListaPronosticoLocalModel(listaPronosticoLocal: List<PronosticoLocal>) : List<PronosticoModel> {
    return listaPronosticoLocal.map {
        PronosticoModel(
            CiudadModel(
                it.nombreCiudad,
                it.nombrePais),
            PronosticoActualModel(
                it.fechaActual,
                it.tempActual,
                it.nubosidadActual,
                it.velocidadVientoActual,
                it.humedadActual,
                it.descripcionActual,
                it.iconoActual),
            listOf(PronosticoDiarioModel())
        )
    }
}