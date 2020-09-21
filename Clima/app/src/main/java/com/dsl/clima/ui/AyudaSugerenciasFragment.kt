package com.dsl.clima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dsl.clima.R
import com.dsl.clima.databinding.FragmentAyudaSugerenciasBinding

class AyudaSugerenciasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAyudaSugerenciasBinding.inflate(inflater)
        return binding.root
    }
}