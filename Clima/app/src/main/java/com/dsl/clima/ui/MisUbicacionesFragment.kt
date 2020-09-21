package com.dsl.clima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dsl.clima.R
import com.dsl.clima.adapter.MisUbicacionesAdapter
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.data.source.local.PronosticoDatabase
import com.dsl.clima.databinding.FragmentMisUbicacionesBinding
import com.dsl.clima.util.mostrarSnackBar
import com.dsl.clima.viewmodel.MisUbicacionesViewModel
import com.dsl.clima.viewmodel.MisUbicacionesViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

        binding.recyclerViewMisUbicaciones.adapter = MisUbicacionesAdapter(
            MisUbicacionesAdapter.OnClickListener {pronosticoModel ->
            this.findNavController().navigate(MisUbicacionesFragmentDirections.actionNavMisUbicacionesToNavHome(pronosticoModel.ciudadModel.idCiudad))
        },
            MisUbicacionesAdapter.OnLongClickListener { pronosticoModel ->
            MaterialAlertDialogBuilder(context)
                .setTitle(getString(R.string.titulo_dialog_mis_ubicaciones))
                .setMessage("Se eliminará ${pronosticoModel.ciudadModel.nombreCiudad}")
                .setNegativeButton(getString(R.string.cancelar_dialog_mis_ubicaciones)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(getString(R.string.eliminar_dialog_mis_ubicaciones)) { dialog, _ ->
                    viewModel.eliminarPronosticoActualizarListaPronostico(pronosticoModel.ciudadModel.idCiudad)
                    mostrarSnackBar(binding.recyclerViewMisUbicaciones, getString(R.string.eliminar_ubicacion))
                    dialog.dismiss()
                }
                .show()
        })

        binding.fabAgregarUbicacion.setOnClickListener {
            this.findNavController().navigate(MisUbicacionesFragmentDirections.actionNavMisUbicacionesToNavAgregarUbicacion())
        }

        viewModel.estadoBasedatos.observe(this, Observer {
            when(it) {
                true -> {
                    binding.shimmerMisUbicaciones.stopShimmer()
                    binding.shimmerMisUbicaciones.visibility = View.GONE
                    binding.recyclerViewMisUbicaciones.visibility = View.VISIBLE
                }
                false -> {
                    binding.shimmerMisUbicaciones.startShimmer()
                    binding.shimmerMisUbicaciones.visibility = View.VISIBLE
                    binding.recyclerViewMisUbicaciones.visibility = View.GONE
                }
            }
        })

        return binding.root
    }
}