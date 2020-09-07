package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.model.DatosMeteorologicosActuales
import com.dsl.clima.data.model.DatosMeteorologicosActualesPrevistos
import com.dsl.clima.data.source.local.ClimaCache
import com.dsl.clima.data.source.remote.climaService
import com.dsl.clima.domain.repository.ClimaRepository
import com.dsl.clima.util.EstadoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val climaRepository: ClimaRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _climaCache = MutableLiveData<ClimaCache>()
    val climaCache: LiveData<ClimaCache>
        get() = _climaCache

    private val _estadoApi = MutableLiveData<EstadoApi>()
    val estadoApi: LiveData<EstadoApi>
        get() = _estadoApi

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        refescarClima("Cordoba")
    }

    fun refescarClima(ciudad: String) {
        coroutineScope.launch {
            _estadoApi.value = climaRepository.estadoApi
            _climaCache.value = climaRepository.refrescarClima(ciudad)
            _estadoApi.value = climaRepository.estadoApi
        }
    }

    /**
     * Se utiliza para destruir el viewModel cuando se destruye el fragmento.
     * De esta manera se libera espacio. Y tambien se cancela alguna corutina [viewModelJob]
     * que se este realizando.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}