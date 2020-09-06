package com.dsl.clima.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsl.clima.domain.repository.ClimaRepository

class HomeViewModelFactory(private val climaRepository: ClimaRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(climaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}