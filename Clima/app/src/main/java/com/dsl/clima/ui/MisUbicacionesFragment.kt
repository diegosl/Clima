package com.dsl.clima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dsl.clima.R
import com.dsl.clima.adapter.MisUbicacionesAdapter
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.data.source.local.PronosticoDatabase
import com.dsl.clima.databinding.FragmentMisUbicacionesBinding
import com.dsl.clima.util.EstadoApi
import com.dsl.clima.util.efectoShimmer
import com.dsl.clima.viewmodel.MisUbicacionesViewModel
import com.dsl.clima.viewmodel.MisUbicacionesViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MisUbicacionesFragment : Fragment() {
    private lateinit var viewModel: MisUbicacionesViewModel
    private lateinit var viewModelFactory: MisUbicacionesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMisUbicacionesBinding.inflate(inflater)
        viewModelFactory = MisUbicacionesViewModelFactory(PronosticoRepository(PronosticoDatabase.getDatebase(activity!!.applicationContext).pronosticoDatabaseDao))
        viewModel = ViewModelProvider(this, viewModelFactory).get(MisUbicacionesViewModel::class.java)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerViewMisUbicaciones.adapter = MisUbicacionesAdapter(MisUbicacionesAdapter.OnClickListener {pronosticoModel ->
            this.findNavController().navigate(MisUbicacionesFragmentDirections.actionNavMisUbicacionesToNavHome(pronosticoModel.ciudadModel.nombreCiudad))
        }, MisUbicacionesAdapter.OnLongClickListener { pronosticoModel ->
            MaterialAlertDialogBuilder(context)
                .setTitle("¿Eliminar ubicación?")
                .setMessage("Se eliminará ${pronosticoModel.ciudadModel.nombreCiudad}")
                .setNegativeButton("Cancelar") { dialog, _ ->
                    // Respond to negative button press
                    dialog.dismiss()
                }
                .setPositiveButton("Eliminar") { dialog, _ ->
                    // Respond to positive button press
                    viewModel.eliminarPronosticoActualizarListaPronostico(pronosticoModel.ciudadModel.nombreCiudad)
                    Snackbar.make(binding.recyclerViewMisUbicaciones, "Ciudad eliminada", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(ContextCompat.getColor(this.context!!, R.color.colorBlanco))
                        .show()
                    dialog.dismiss()
                }
                .show()
        })

        binding.fabAgregarUbicacion.setOnClickListener {
            this.findNavController().navigate(MisUbicacionesFragmentDirections.actionNavMisUbicacionesToNavAgregarUbicacion())
        }

        binding.swipeRefreshMisUbicaciones.setColorSchemeColors(ContextCompat.getColor(this.context!!, R.color.colorPrimary))
        binding.swipeRefreshMisUbicaciones.setOnRefreshListener {
            viewModel.getMisUbicaciones()
        }

        viewModel.estadoApi.observe(this, Observer {
            efectoShimmer(it, binding.shimmerMisUbicaciones, binding.recyclerViewMisUbicaciones, binding.swipeRefreshMisUbicaciones)
        })

        return binding.root
    }
}