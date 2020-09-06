package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.model.DatosMeteorologicosActuales
import com.dsl.clima.data.model.DatosMeteorologicosActualesPrevistos
import com.dsl.clima.data.source.remote.climaService
import com.dsl.clima.domain.repository.ClimaRepository
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val climaRepository: ClimaRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _datosMeteorologicosActuales = MutableLiveData<DatosMeteorologicosActuales>()
    val datosMeteorologicosActuales: LiveData<DatosMeteorologicosActuales>
        get() = _datosMeteorologicosActuales

    private val _datosMeteorologicosActualesPrevistos = MutableLiveData<DatosMeteorologicosActualesPrevistos>()
    val datosMeteorologicosActualesPrevistos: LiveData<DatosMeteorologicosActualesPrevistos>
        get() = _datosMeteorologicosActualesPrevistos

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
       //getClima("Cordoba")
    }

    /*fun getClima(ciudad: String) {
        coroutineScope.launch {
            val getDatosMeteorologicosActualesDeferred = climaService.retrofitService.getDatosMeteorologicosActuales(ciudad)
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                val result_1 = getDatosMeteorologicosActualesDeferred.await()
                val getDatosMeteorologicosActualesPrevistosDeferred = climaService.retrofitService.getDatosMeteorologicosActualesPrevistos(
                    result_1.coordenadaCiudad.latitud, result_1.coordenadaCiudad.longitud
                )
                val result_2 = getDatosMeteorologicosActualesPrevistosDeferred.await()
                _estadoApi.value = EstadoApi.FINALIZADO
                _datosMeteorologicosActuales.value = result_1
                _datosMeteorologicosActualesPrevistos.value = result_2
            } catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _datosMeteorologicosActuales.value =
                    DatosMeteorologicosActuales()
                _datosMeteorologicosActualesPrevistos.value =
                    DatosMeteorologicosActualesPrevistos()
            }
        }
    }*/



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