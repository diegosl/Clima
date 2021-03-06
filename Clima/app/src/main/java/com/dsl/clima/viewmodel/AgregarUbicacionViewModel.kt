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

    private val _listaCiudadModel = MutableLiveData<List<CiudadModel>>()
    val listaCiudadModel: LiveData<List<CiudadModel>>
        get() = _listaCiudadModel

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    private val _estadoBusqueda = MutableLiveData<Boolean>()
    val estadoBusqueda: LiveData<Boolean>
        get() = _estadoBusqueda

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        _estadoBusqueda.value = true
    }

    fun getCiudad(nombreCiudad: String) {
        coroutineScope.launch {
            _estadoBusqueda.value = false
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                _listaCiudadModel.value = pronosticoRepository.getListaCiudad(nombreCiudad)
                _estadoApi.value = EstadoApi.FINALIZADO
            }
            catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _listaCiudadModel.value = listOf(CiudadModel())
            }
        }
    }

    fun insertarPronostico(ciudadModel: CiudadModel) {
        coroutineScope.launch {
            val pronosticoModel = PronosticoModel(ciudadModel = ciudadModel)
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