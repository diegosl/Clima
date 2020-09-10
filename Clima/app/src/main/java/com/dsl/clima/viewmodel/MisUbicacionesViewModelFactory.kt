package com.dsl.clima.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsl.clima.data.repository.PronosticoRepository

class MisUbicacionesViewModelFactory(private val pronosticoRepository: PronosticoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MisUbicacionesViewModel::class.java)) {
            return MisUbicacionesViewModel(pronosticoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}