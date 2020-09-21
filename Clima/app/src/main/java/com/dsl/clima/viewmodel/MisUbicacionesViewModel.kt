package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.repository.PronosticoRepository
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

    private val _estadoBasedatos = MutableLiveData<Boolean>()
    val estadoBasedatos: LiveData<Boolean>
        get() = _estadoBasedatos

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        getMisUbicaciones()
    }

    fun getMisUbicaciones() {
        coroutineScope.launch {
            _estadoBasedatos.value = false
            _listaPronosticoModel.value = pronosticoRepository.getListaPronostico()
            _estadoBasedatos.value = true
        }
    }

    fun eliminarPronosticoActualizarListaPronostico(idCiudad: Int) {
        coroutineScope.launch {
            pronosticoRepository.eliminarPronostico(idCiudad)
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