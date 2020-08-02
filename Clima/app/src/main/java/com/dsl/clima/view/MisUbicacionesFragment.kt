package com.dsl.clima.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dsl.clima.R
import com.dsl.clima.databinding.FragmentMisUbicacionesBinding

class MisUbicacionesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMisUbicacionesBinding.inflate(inflater)
        return binding.root
    }
}