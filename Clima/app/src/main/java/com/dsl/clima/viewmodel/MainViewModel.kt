package com.dsl.clima.viewmodel

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.service.ServicioLocalizacion

class MainViewModel(private val context: Context) : ViewModel() {
    private val _estadoLocalizacion = MutableLiveData<Boolean>()
    val estadoLocalizacion: LiveData<Boolean>
        get() = _estadoLocalizacion

    private var servicioLocalizacion: ServicioLocalizacion = ServicioLocalizacion(context)

    init {
        chequearProviderLocalizacion()
    }

    fun chequearProviderLocalizacion() {
        if(servicioLocalizacion.chequearPermiso()) {

        }
        else {
            _estadoLocalizacion.value = servicioLocalizacion.chequearProviders()
        }
    }

    fun getLocalizacion() {
        servicioLocalizacion.getLocalizacion()
    }
}