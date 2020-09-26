package com.dsl.clima.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsl.clima.R
import com.dsl.clima.adapter.PronosticoExtendidoAdapter
import com.dsl.clima.databinding.FragmentHomeBinding
import com.dsl.clima.data.repository.PronosticoRepository
import com.dsl.clima.data.source.local.PronosticoDatabase.Companion.getDatebase
import com.dsl.clima.util.efectoShimmer
import com.dsl.clima.viewmodel.HomeViewModel
import com.dsl.clima.viewmodel.HomeViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

        var idCiudad: Int

        try {
            idCiudad = HomeFragmentArgs.fromBundle(requireArguments()).idCiudad
        }catch (e: Exception) {
            idCiudad = -1
        }

        viewModelFactory = HomeViewModelFactory(context!!, PronosticoRepository(getDatebase(activity!!.applicationContext).pronosticoDatabaseDao), idCiudad)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        /**
         * Cuando el usuario desliza para actualizar swipeRefreshHome
         * se actuliza la lista de productos products.
         */
        binding.swipeRefreshHome.setColorSchemeColors(ContextCompat.getColor(this.context!!, R.color.colorPrimary))
        binding.swipeRefreshHome.setOnRefreshListener {
            viewModel.refescarPronostico()
        }

        /**
         * El fragmento [HomeFragment] observa la variable statusApi para determinar si fue o no
         * posible conectarse con el servicio web.
         * En caso que se produzca un exito DONE o un error ERROR, el swipeRefreshProduct se deja de mostrar.
         * En caso que se produzca un error ERROR, mostrara un SnackBar.
         */
        viewModel.estadoApi.observe(this, Observer {
            efectoShimmer(it, binding.shimmerHome, binding.layoutHome, binding.swipeRefreshHome, getString(R.string.error_internet))
        })

        viewModel.estadoLocalizacion.observe(this, Observer {
            when(it) {
                true -> {
                    viewModel.refescarPronostico()
                }
                false -> {
                    MaterialAlertDialogBuilder(context)
                        .setTitle("¿Activar servicio de localización?")
                        .setMessage("Para usar su ubicacion actual, debe activar Ubicación en Ajustes.")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar") { dialog, _ ->
                            activity?.finish()
                            dialog.dismiss()
                        }
                        .setPositiveButton("Ajustes") { dialog, _ ->
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(intent)
                        }
                        .show()
                }
            }
        })

        binding.recyclerViewPronosticoExtendido.adapter = PronosticoExtendidoAdapter(PronosticoExtendidoAdapter.OnClickListener {
        })

        return  binding.root
    }
}