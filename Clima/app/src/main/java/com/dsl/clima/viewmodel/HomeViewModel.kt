package com.dsl.clima.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.domain.model.PronosticoModel
import com.dsl.clima.service.chequearProviders
import com.dsl.clima.service.estadoLocalizacion
import com.dsl.clima.service.getUserLocation
import com.dsl.clima.service.locationObs
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

    private val _estadoGPS = MutableLiveData<Boolean>()
    val estadoGPS: LiveData<Boolean>
        get() = _estadoGPS

    val estadoUbicacion: LiveData<EstadoLocalizacion>
        get() = estadoLocalizacion

    fun chequearProviderLocalizacion() {
        chequearProviders(context)
    }

    fun refescarPronostico() {
        coroutineScope.launch {
            try {
                if (idCiudad == -1) {
                    _estadoApi.value = EstadoApi.CARGANDO
                    _estadoGPS.value = true
                    getUserLocation(context)
                    _pronosticoModel.value = locationObs.value?.latitude?.let {
                        locationObs.value?.longitude?.let { it1 ->
                            pronosticoRepository.refrescarPronostico(
                                it,
                                it1
                            )
                        }
                    }
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