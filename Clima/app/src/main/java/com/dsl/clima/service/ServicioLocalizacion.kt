package com.dsl.clima.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dsl.clima.util.EstadoLocalizacion

class ServicioLocalizacion(private val context: Context) {
    companion object {
        private var latitud: Double = 0.0
        private var longitud: Double = 0.0
    }

    private val _estadoLocalizacion = MutableLiveData<EstadoLocalizacion>()
    val estadoLocalizacion: LiveData<EstadoLocalizacion>
        get() = _estadoLocalizacion

    private var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitud = location.latitude
            longitud = location.longitude
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {
            _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_APROBADO
        }
        override fun onProviderDisabled(provider: String) {
            _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_DENEGADO
        }
    }

    @SuppressLint("MissingPermission")
    fun getCoordenadas(): List<Double> {
        val location = locationManager.getLastKnownLocation(getProviderHabilitado())
        location?.let {
            latitud = it.latitude
            longitud = it.longitude
        }
        return listOf(latitud, longitud)
    }

    fun chequearPermiso() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                _estadoLocalizacion.value = EstadoLocalizacion.PERMISO_APROBADO
            }
            else {
                _estadoLocalizacion.value = EstadoLocalizacion.PERMISO_DENEGADO
            }
            }
        else {
            _estadoLocalizacion.value = EstadoLocalizacion.PERMISO_APROBADO
        }
    }

    @SuppressLint("MissingPermission")
    fun chequearProviders() {
        //locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return if(LocationManagerCompat.isLocationEnabled(locationManager)) {
            locationManager.requestLocationUpdates(getProviderHabilitado(), 0L, 0f, locationListener)
            _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_APROBADO
        } else {
            _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_DENEGADO
        }
    }

    @SuppressLint("MissingPermission")
    fun getProviderHabilitado(): String {
        val providers = locationManager.getProviders(true)
        var providerHabilitado = ""
        for (provider in providers) locationManager.getLastKnownLocation(provider).let {
            providerHabilitado = provider
        }
        return providerHabilitado
    }

    fun removerActualizacionLocalizacion() {
        locationManager.removeUpdates(locationListener)
    }
}