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

class HomeViewModel(private val pronosticoRepository: PronosticoRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _pronosticoModel = MutableLiveData<PronosticoModel>()
    val pronosticoModel: LiveData<PronosticoModel>
        get() = _pronosticoModel

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        refescarClima("Cordoba")
    }

    fun refescarClima(ciudad: String) {
        coroutineScope.launch {
            _pronosticoModel.value = pronosticoRepository.refrescarPronostico(ciudad)
            _estadoApi.value = pronosticoRepository.estadoApi
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