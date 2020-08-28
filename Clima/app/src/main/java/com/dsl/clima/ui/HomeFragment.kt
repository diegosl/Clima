package com.dsl.clima.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dsl.clima.R
import com.dsl.clima.databinding.FragmentHomeBinding
import com.dsl.clima.util.efectoShimmer
import com.dsl.clima.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        binding.fabAgregarUbicacion.setOnClickListener {
            this.findNavController().navigate(R.layout.fragment_agregar_ubicacion)
        }

        /**
         * El fragmento [HomeFragment] observa la variable statusApi para determinar si fue o no
         * posible conectarse con el servicio web.
         * En caso que se produzca un exito DONE o un error ERROR, el swipeRefreshProduct se deja de mostrar.
         * En caso que se produzca un error ERROR, mostrara un SnackBar.
         */
        viewModel.estadoApi.observe(this, Observer {
            efectoShimmer(it, binding.shimmerHome, binding.layoutHome, binding.swipeRefreshHome)
        })

        /**
         * Cuando el usuario desliza para actualizar swipeRefreshHome
         * se actuliza la lista de productos products.
         */
        binding.swipeRefreshHome.setColorSchemeColors(ContextCompat.getColor(this.context!!, R.color.colorPrimary))
        binding.swipeRefreshHome.setOnRefreshListener {
            viewModel.getClima("Cordoba")
        }

        return  binding.root
    }
}