package com.dsl.clima.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsl.clima.data.repository.PronosticoRepository

class AgregarUbicacionViewModelFactory(private val pronosticoRepository: PronosticoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgregarUbicacionViewModel::class.java)) {
            return AgregarUbicacionViewModel(pronosticoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}