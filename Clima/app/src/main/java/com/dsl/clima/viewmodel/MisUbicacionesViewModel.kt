package com.dsl.clima.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsl.clima.data.Pronostico
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MisUbicacionesViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _pronosticos = MutableLiveData<List<Pronostico>>()
    val pronosticos: LiveData<List<Pronostico>>
        get() = _pronosticos

    private val _pronosticoSeleccionado = MutableLiveData<Pronostico>()
    val pronosticoSeleccionado: LiveData<Pronostico>
        get() = _pronosticoSeleccionado

    private val listaPronosticos = ArrayList<Pronostico>()

    /**
     * Inicializa el modelo de vista viewModel.
     */
    init {
        getMisUbicaciones()
    }

    private fun getMisUbicaciones() {
        listaPronosticos.add(Pronostico(1, "Villa Giardino, Córdoba Argentina", "Lunes 3 de Agosto", "27°", "", "Dia Despejado"))
        listaPronosticos.add(Pronostico(2, "Capital, Córdoba Argentina", "Lunes 3 de Agosto", "20°", "", "Tarde Despejado"))
        listaPronosticos.add(Pronostico(3, "Miami, Florida Estados Unidos", "Lunes 3 de Agosto", "21°", "", "Noche Despejado"))
        listaPronosticos.add(Pronostico(4, "Tokio, Japan", "Martes 4 de Agosto", "10°", "", "Dia Nublado"))
        _pronosticos.value = listaPronosticos
    }

    fun navegarPronosticoSeleccionado(pronostico: Pronostico) {
        _pronosticoSeleccionado.value = pronostico
    }

    fun navegarPronosticoSeleccionadoCompletado() {
        _pronosticoSeleccionado.value = null
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