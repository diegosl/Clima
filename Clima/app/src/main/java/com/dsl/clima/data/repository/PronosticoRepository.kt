package com.dsl.clima.data.repository

import com.dsl.clima.data.source.local.PronosticoDatabaseDao
import com.dsl.clima.data.source.local.PronosticoDiarioLocal
import com.dsl.clima.data.source.local.PronosticoLocal
import com.dsl.clima.data.source.remote.*
import com.dsl.clima.domain.model.CiudadModel
import com.dsl.clima.domain.model.PronosticoActualModel
import com.dsl.clima.domain.model.PronosticoDiarioModel
import com.dsl.clima.domain.model.PronosticoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PronosticoRepository(private val dataLocal: PronosticoDatabaseDao) {
    suspend fun refrescarPronostico(idCiudad: Int): PronosticoModel {
        var pronosticoModel = PronosticoModel()
        withContext(Dispatchers.IO) {
            val ciudadRemote = apiService.retrofitService.getCiudad(idCiudad).await()
            val pronosticoRemote = apiService.retrofitService.getPronostico(ciudadRemote.coordenadaCiudad.latitud, ciudadRemote.coordenadaCiudad.longitud).await()
            pronosticoModel = transformarPronosticoRemoteModel(ciudadRemote, pronosticoRemote)
            val pronosticoLocal = transformarPronosticoModelLocal(pronosticoModel)
            dataLocal.actualizarPronosticoLocal(pronosticoLocal)
        }
        return pronosticoModel
    }

    suspend fun refrescarPronostico(latitudCiudad: Double, longitudCiudad: Double): PronosticoModel {
        var pronosticoModel = PronosticoModel()
        withContext(Dispatchers.IO) {
            val ciudadRemote = apiService.retrofitService.getCiudad(latitudCiudad, longitudCiudad).await()
            val pronosticoRemote = apiService.retrofitService.getPronostico(ciudadRemote.coordenadaCiudad.latitud, ciudadRemote.coordenadaCiudad.longitud).await()
            pronosticoModel = transformarPronosticoRemoteModel(ciudadRemote, pronosticoRemote)
            //val pronosticoLocal = transformarPronosticoModelLocal(pronosticoModel)
        }
        return pronosticoModel
    }

    suspend fun getListaCiudad(nombreCiudad: String): List<CiudadModel> {
        var listaCiudadModel = listOf(CiudadModel())
        withContext(Dispatchers.IO) {
            val listaCiudadRemote = apiService.retrofitService.getListaCiudad(nombreCiudad).await()
            listaCiudadModel = transformarListaCiudadRemoteModel(listaCiudadRemote)
        }
        return listaCiudadModel
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

    suspend fun getPronostico(idCiudad: Int): PronosticoModel {
        var pronosticoModel = PronosticoModel()
        withContext(Dispatchers.IO) {
            pronosticoModel = transformarPronosticoLocalModel(dataLocal.getPronosticoLocal(idCiudad))
        }
        return pronosticoModel
    }

    suspend fun eliminarPronostico(idCiudad: Int) {
        withContext(Dispatchers.IO) {
            dataLocal.eliminarPronosticoLocal(idCiudad)
        }
    }
}

fun transformarPronosticoRemoteModel(ciudadRemote: CiudadRemote, pronosticoRemote: PronosticoRemote) : PronosticoModel {
    return PronosticoModel(
        CiudadModel(
            ciudadRemote.id,
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

fun transformarListaCiudadRemoteModel(listaCiudadRemote: ListaCiudadRemote): List<CiudadModel> {
    return listaCiudadRemote.listaCiudadRemote.map {
        CiudadModel(it.id, it.nombreCiudad, it.sys.nombrePais)
    }
}

fun transformarPronosticoModelLocal(pronosticoModel: PronosticoModel) : PronosticoLocal {
    return PronosticoLocal(
        pronosticoModel.ciudadModel.idCiudad,
        pronosticoModel.ciudadModel.nombreCiudad,
        pronosticoModel.ciudadModel.nombrePais,
        pronosticoModel.pronosticoActualModel.fechaActual,
        pronosticoModel.pronosticoActualModel.temperaturaActual,
        pronosticoModel.pronosticoActualModel.humedadActual,
        pronosticoModel.pronosticoActualModel.nubosidadActual,
        pronosticoModel.pronosticoActualModel.velocidadVientoActual,
        pronosticoModel.pronosticoActualModel.descripcionActual,
        pronosticoModel.pronosticoActualModel.iconoActual,
        transformarListaPronosticoDiarioModelLocal(pronosticoModel.listaPronosticoDiarioModel)
    )
}

fun transformarListaPronosticoLocalModel(listaPronosticoLocal: List<PronosticoLocal>) : List<PronosticoModel> {
    return listaPronosticoLocal.map {
        PronosticoModel(
            CiudadModel(
                it.idCiudad,
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
            transformarListaPronosticoDiarioLocalModel(it.listaPronosticoDiarioLocal)
        )
    }
}

fun transformarPronosticoLocalModel(pronosticoLocal: PronosticoLocal) : PronosticoModel {
    return PronosticoModel(
        CiudadModel(
            pronosticoLocal.idCiudad,
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
        transformarListaPronosticoDiarioLocalModel(pronosticoLocal.listaPronosticoDiarioLocal)
    )
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