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
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dsl.clima.util.EstadoLocalizacion

@SuppressLint("MissingPermission")
fun getUserLocation(context: Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val location = locationManager.getLastKnownLocation(getProviderHabilitado(context))
    location?.let {
        _locationObs.value = it
    }
}

fun chequearPermiso(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
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
fun chequearProviders(context: Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if(LocationManagerCompat.isLocationEnabled(locationManager)) {
        locationManager.requestLocationUpdates(getProviderHabilitado(context), 0L, 0f, locationListener)
        _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_APROBADO
    } else {
        _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_DENEGADO
    }
}

@SuppressLint("MissingPermission")
private fun getProviderHabilitado(context: Context): String {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers = locationManager.getProviders(true)
    var providerHabilitado = ""
    for (provider in providers) locationManager.getLastKnownLocation(provider).let {
        providerHabilitado = provider
    }
    return providerHabilitado
}

fun removerActualizacionLocalizacion(context: Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    locationManager.removeUpdates(locationListener)
}

private val _locationObs = MutableLiveData<Location>()
val locationObs: LiveData<Location>
    get() = _locationObs

private val _estadoLocalizacion = MutableLiveData<EstadoLocalizacion>()
val estadoLocalizacion: LiveData<EstadoLocalizacion>
    get() = _estadoLocalizacion

private val locationListener = object : LocationListener {
    override fun onLocationChanged(location: Location) {
        _locationObs.value = location
    }
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {
        _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_APROBADO
    }
    override fun onProviderDisabled(provider: String) {
        _estadoLocalizacion.value = EstadoLocalizacion.PROVEDOR_DENEGADO
    }
}