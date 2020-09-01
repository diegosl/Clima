package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.api.apiService
import com.dsl.clima.data.Ciudad
import com.dsl.clima.data.Clima
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _ciudad = MutableLiveData<Ciudad>()
    val ciudad: LiveData<Ciudad>
        get() = _ciudad

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        getClima("Córdoba")
    }

    fun getClima(nombreCiudad: String) {
        coroutineScope.launch {
            val getCiudadDeferred = apiService.retrofitService.getCiudad(nombreCiudad)
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                val result = getCiudadDeferred.await()
                _estadoApi.value = EstadoApi.FINALIZADO
                _ciudad.value = result
            } catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _ciudad.value = Ciudad()
            }
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