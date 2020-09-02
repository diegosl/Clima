package com.dsl.clima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.dsl.clima.adapter.MisUbicacionesAdapter
import com.dsl.clima.databinding.FragmentMisUbicacionesBinding
import com.dsl.clima.viewmodel.MisUbicacionesViewModel

class MisUbicacionesFragment : Fragment() {
    private val viewModel: MisUbicacionesViewModel by lazy {
        ViewModelProviders.of(this).get(MisUbicacionesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMisUbicacionesBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerViewMisUbicaciones.adapter = MisUbicacionesAdapter(MisUbicacionesAdapter.OnClickListener {
        })

        return binding.root
    }
}