package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.data.source.remote.apiService
import com.dsl.clima.domain.model.PronosticoActualModel
import com.dsl.clima.domain.model.PronosticoModel
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MisUbicacionesViewModel(private val pronosticoRepository: PronosticoRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _listaPronosticoModel = MutableLiveData<List<PronosticoModel>>()
    val listaPronosticoModel: LiveData<List<PronosticoModel>>
        get() = _listaPronosticoModel

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        getMisUbicaciones()
    }

    fun getMisUbicaciones() {
        coroutineScope.launch {
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                _listaPronosticoModel.value = pronosticoRepository.getListaPronostico()
                _estadoApi.value = EstadoApi.FINALIZADO
            }
            catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _listaPronosticoModel.value = ArrayList()
            }
        }
    }

    fun eliminarPronosticoActualizarListaPronostico(nombreCiudad: String) {
        coroutineScope.launch {
            pronosticoRepository.eliminarPronostico(nombreCiudad)
            _listaPronosticoModel.value = pronosticoRepository.getListaPronostico()
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