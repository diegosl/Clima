package com.dsl.clima.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsl.clima.data.repository.PronosticoRepository

class HomeViewModelFactory(private val context: Context, private val pronosticoRepository: PronosticoRepository, private val idCiudad: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(context, pronosticoRepository, idCiudad) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}