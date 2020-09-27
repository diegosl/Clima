package com.dsl.clima.viewmodel

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.service.ServicioLocalizacion
import com.dsl.clima.util.EstadoLocalizacion

class MainViewModel(private val context: Context) : ViewModel() {
    var servicioLocalizacion: ServicioLocalizacion = ServicioLocalizacion(context)

    init {
        chequearPermisoLocalizacion()
    }

    fun chequearPermisoLocalizacion() {
        servicioLocalizacion.chequearPermiso()
    }

    fun chequearProviderLocalizacion() {
        servicioLocalizacion.chequearProviders()
    }

    override fun onCleared() {
        super.onCleared()
        servicioLocalizacion.removerActualizacionLocalizacion()
    }
}