package com.dsl.clima.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.domain.model.PronosticoModel
import com.dsl.clima.service.ServicioLocalizacion
import com.dsl.clima.util.EstadoApi
import com.dsl.clima.util.EstadoLocalizacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context, private val pronosticoRepository: PronosticoRepository, private val idCiudad: Int) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _pronosticoModel = MutableLiveData<PronosticoModel>()
    val pronosticoModel: LiveData<PronosticoModel>
        get() = _pronosticoModel

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    var servicioLocalizacion: ServicioLocalizacion = ServicioLocalizacion(context)

    private val _estadoGPS = MutableLiveData<Boolean>()
    val estadoGPS: LiveData<Boolean>
        get() = _estadoGPS

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        servicioLocalizacion.chequearProviders()
    }

    fun refescarPronostico() {
        coroutineScope.launch {
            try {
                if (idCiudad == -1) {
                    _estadoApi.value = EstadoApi.CARGANDO
                    _estadoGPS.value = true
                    val coordenadas = servicioLocalizacion.getCoordenadas()
                    _pronosticoModel.value = pronosticoRepository.refrescarPronostico(coordenadas[0], coordenadas[1])
                    _estadoApi.value = EstadoApi.FINALIZADO
                } else {
                    _estadoApi.value = EstadoApi.CARGANDO
                    _estadoGPS.value = false
                    _pronosticoModel.value = pronosticoRepository.refrescarPronostico(idCiudad)
                    _estadoApi.value = EstadoApi.FINALIZADO
                }
            }
            catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _estadoGPS.value = false
                try {
                    _pronosticoModel.value = pronosticoRepository.getPronostico(idCiudad)
                }
                catch(e: Exception) {
                    _pronosticoModel.value = PronosticoModel()
                }
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