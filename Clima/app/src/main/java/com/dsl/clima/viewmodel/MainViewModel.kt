package com.dsl.clima.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.service.chequearPermiso
import com.dsl.clima.service.chequearProviders
import com.dsl.clima.service.estadoLocalizacion
import com.dsl.clima.service.removerActualizacionLocalizacion
import com.dsl.clima.util.EstadoLocalizacion

class MainViewModel(private val context: Context) : ViewModel() {
    val estadoUbicacion: LiveData<EstadoLocalizacion>
        get() = estadoLocalizacion

    init {
        chequearPermisoLocalizacion()
    }

    fun chequearPermisoLocalizacion() {
        chequearPermiso(context)
    }

    fun chequearProviderLocalizacion() {
        chequearProviders(context)
    }

    override fun onCleared() {
        super.onCleared()
        removerActualizacionLocalizacion(context)
    }
}