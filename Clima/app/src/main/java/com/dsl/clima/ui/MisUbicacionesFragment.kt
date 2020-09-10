package com.dsl.clima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dsl.clima.adapter.MisUbicacionesAdapter
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.data.source.local.PronosticoDatabase
import com.dsl.clima.databinding.FragmentMisUbicacionesBinding
import com.dsl.clima.viewmodel.HomeViewModel
import com.dsl.clima.viewmodel.HomeViewModelFactory
import com.dsl.clima.viewmodel.MisUbicacionesViewModel
import com.dsl.clima.viewmodel.MisUbicacionesViewModelFactory

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

        binding.recyclerViewMisUbicaciones.adapter = MisUbicacionesAdapter(MisUbicacionesAdapter.OnClickListener {
        })

        return binding.root
    }
}