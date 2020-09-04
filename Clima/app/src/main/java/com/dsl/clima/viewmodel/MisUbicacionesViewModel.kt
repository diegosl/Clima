package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.source.remote.apiService
import com.dsl.clima.domain.DatosMeteorologicosActuales
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MisUbicacionesViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _ciudades = MutableLiveData<List<DatosMeteorologicosActuales>>()
    val ciudades: LiveData<List<DatosMeteorologicosActuales>>
        get() = _ciudades

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        getMisUbicaciones()
    }

    private fun getMisUbicaciones() {
        coroutineScope.launch {
            val getMisUbicacionesDeferred = apiService.retrofitService.getListaDatosMeteorologicosActuales("London")
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                val listResult = getMisUbicacionesDeferred.await()
                _estadoApi.value = EstadoApi.FINALIZADO
                _ciudades.value = listResult
            } catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _ciudades.value = ArrayList()
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