package com.dsl.clima.data.repository

import com.dsl.clima.data.source.local.PronosticoDatabaseDao
import com.dsl.clima.data.source.local.PronosticoDiarioLocal
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
            val ciudadRemote = apiService.retrofitService.getCiudad(nombreCiudad).await()
            val pronosticoRemote = apiService.retrofitService.getPronostico(ciudadRemote.coordenadaCiudad.latitud, ciudadRemote.coordenadaCiudad.longitud).await()
            pronosticoModel = transformarPronosticoRemoteModel(ciudadRemote, pronosticoRemote)
            val pronosticoLocal = transformarPronosticoModelLocal(pronosticoModel)
            val listaPronosticoDiarioLocal = transformarListaPronosticoDiarioModelLocal(pronosticoModel.listaPronosticoDiarioModel)
            dataLocal.actualizarPronosticoLocal(pronosticoLocal)
            dataLocal.actualizarPronosticoDiarioLocal(listaPronosticoDiarioLocal)
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

    suspend fun insertarPronosticoDiario(pronosticoModel: PronosticoModel) {
        withContext(Dispatchers.IO) {
            val pronosticoDiarioLocal = transformarPronosticoDiarioModelLocal(pronosticoModel)
            dataLocal.insertarPronosticoDiarioLocal(pronosticoDiarioLocal)
        }
    }

    suspend fun getListaPronostico(): List<PronosticoModel> {
        var listaPronosticoModel = listOf(PronosticoModel())
        withContext(Dispatchers.IO) {
            listaPronosticoModel = transformarListaPronosticoLocalModel(dataLocal.getListaPronosticoLocal())
        }
        return listaPronosticoModel
    }

    suspend fun getListaPronosticoDiario(nombreCiudad: String): List<PronosticoDiarioModel> {
        var listaPronosticoDiarioModel = listOf(PronosticoDiarioModel())
        withContext(Dispatchers.IO) {
            listaPronosticoDiarioModel = transformarListaPronosticoDiarioLocalModel(dataLocal.getListaPronosticoDiarioLocal(nombreCiudad))
        }
        return listaPronosticoDiarioModel
    }

    suspend fun getPronostico(nombreCiudad: String): PronosticoModel {
        var pronosticoModel = PronosticoModel()
        withContext(Dispatchers.IO) {
            pronosticoModel = transformarPronosticoLocalModel(dataLocal.getPronosticoLocal(nombreCiudad))
        }
        return pronosticoModel
    }

    suspend fun eliminarPronostico(nombreCiudad: String) {
        withContext(Dispatchers.IO) {
            dataLocal.eliminarPronosticoLocal(nombreCiudad)
        }
    }

    suspend fun eliminarPronosticoDiario(nombreCiudad: String) {
        withContext(Dispatchers.IO) {
            dataLocal.eliminarPronosticoDiarioLocal(nombreCiudad)
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
                transformarListaPronosticoDiarioLocalModel(dataLocal.getListaPronosticoDiarioLocal(it.nombreCiudad))
            )
        }
    }

    fun transformarPronosticoLocalModel(pronosticoLocal: PronosticoLocal) : PronosticoModel {
        return PronosticoModel(
            CiudadModel(
                pronosticoLocal.nombreCiudad,
                pronosticoLocal.nombrePais),
            PronosticoActualModel(
                pronosticoLocal.fechaActual,
                pronosticoLocal.tempActual,
                pronosticoLocal.nubosidadActual,
                pronosticoLocal.velocidadVientoActual,
                pronosticoLocal.humedadActual,
                pronosticoLocal.descripcionActual,
                pronosticoLocal.iconoActual),
            transformarListaPronosticoDiarioLocalModel(dataLocal.getListaPronosticoDiarioLocal(pronosticoLocal.nombreCiudad))
        )
    }

    fun transformarPronosticoDiarioModelLocal(pronosticoModel: PronosticoModel) : List<PronosticoDiarioLocal> {
        return pronosticoModel.listaPronosticoDiarioModel.map {
            PronosticoDiarioLocal(
                nombreCiudad = pronosticoModel.ciudadModel.nombreCiudad,
                fechaDiaria = it.fechaDiaria,
                temperaturaMin = it.temperaturaMin,
                temperaturaMax = it.temperaturaMax,
                iconoDiario = it.iconoDiario
            )
        }
    }

    fun transformarListaPronosticoDiarioLocalModel(listaPronosticoDiarioLocal: List<PronosticoDiarioLocal>) : List<PronosticoDiarioModel> {
        return listaPronosticoDiarioLocal.map {
            PronosticoDiarioModel(
                fechaDiaria = it.fechaDiaria,
                temperaturaMin = it.temperaturaMin,
                temperaturaMax = it.temperaturaMax,
                iconoDiario = it.iconoDiario
            )
        }
    }

    fun transformarListaPronosticoDiarioModelLocal(listaPronosticoDiarioModel: List<PronosticoDiarioModel>) : List<PronosticoDiarioLocal> {
        return listaPronosticoDiarioModel.map {
            PronosticoDiarioLocal(
                fechaDiaria = it.fechaDiaria,
                temperaturaMin = it.temperaturaMin,
                temperaturaMax = it.temperaturaMax,
                iconoDiario = it.iconoDiario
            )
        }
    }
}