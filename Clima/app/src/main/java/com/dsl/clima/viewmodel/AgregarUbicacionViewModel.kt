package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.domain.model.CiudadModel
import com.dsl.clima.domain.model.PronosticoActualModel
import com.dsl.clima.domain.model.PronosticoModel
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AgregarUbicacionViewModel(private val pronosticoRepository: PronosticoRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _ciudadModel = MutableLiveData<CiudadModel>()
    val ciudadModel: LiveData<CiudadModel>
        get() = _ciudadModel

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        getCiudad("")
    }

    fun getCiudad(nombreCiudad: String) {
        coroutineScope.launch {
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                _ciudadModel.value = pronosticoRepository.getCiudad(nombreCiudad)
                _estadoApi.value = EstadoApi.FINALIZADO
            }
            catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _ciudadModel.value = CiudadModel()
            }
        }
    }

    fun insertarPronostico() {
        coroutineScope.launch {
            val pronosticoModel = PronosticoModel(ciudadModel = _ciudadModel.value!!)
            pronosticoRepository.insertarPronostico(pronosticoModel)
        }
    }

    /**
     * Se utiliza para destruir el viewModel cuando se destruye el fragmento.
     * De esta manera se libera espacio. Y tambien se cancela alguna corutina [viewModelJob]
     * que se este realizando.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}