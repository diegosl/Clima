package com.dsl.clima.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsl.clima.data.repository.PronosticoRepository

class HomeViewModelFactory(private val pronosticoRepository: PronosticoRepository, private val idCiudad: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(pronosticoRepository, idCiudad) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}