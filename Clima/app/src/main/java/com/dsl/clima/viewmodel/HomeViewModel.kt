package com.dsl.clima.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
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

    private val _estadoLocalizacion = MutableLiveData<Boolean>()
    val estadoLocalizacion: LiveData<Boolean>
        get() = _estadoLocalizacion

    private var latitud: Double
    private var longitud: Double

    private lateinit var locationManager: LocationManager
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitud = location.latitude
            longitud = location.longitude
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {
            _estadoLocalizacion.value = true
        }
        override fun onProviderDisabled(provider: String) {
            _estadoLocalizacion.value = false
        }
    }

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        latitud = 0.0
        longitud = 0.0
        refescarPronostico()
    }

    fun refescarPronostico() {
        coroutineScope.launch {
            try {
                _estadoApi.value = EstadoApi.CARGANDO
                locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (ActivityCompat.checkSelfPermission(context!!.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context!!.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //_estadoLocalizacion.value = true
                }
                else {
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        _estadoLocalizacion.value = false
                    }
                    else {
                        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if(location != null) {
                            latitud = location.latitude
                            longitud = location.longitude
                        }

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0f, locationListener)

                        _pronosticoModel.value = pronosticoRepository.refrescarPronostico(idCiudad, latitud, longitud)
                        _estadoApi.value = EstadoApi.FINALIZADO
                    }
                }
            }
            catch (e: Exception) {
                _estadoApi.value = EstadoApi.ERROR
                _pronosticoModel.value = pronosticoRepository.getPronostico(idCiudad)
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
        //locationManager.removeUpdates(locationListener)
        viewModelJob.cancel()
    }
}