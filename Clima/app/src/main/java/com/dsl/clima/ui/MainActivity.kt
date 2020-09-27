package com.dsl.clima.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dsl.clima.R
import com.dsl.clima.databinding.ActivityMainBinding
import com.dsl.clima.util.EstadoLocalizacion
import com.dsl.clima.util.mostrarSnackBar
import com.dsl.clima.viewmodel.MainViewModel
import com.dsl.clima.viewmodel.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)
        val navController = this.findNavController(R.id.nav_host_fragment)
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        viewModelFactory = MainViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.servicioLocalizacion.estadoLocalizacion.observe(this, Observer {
            when(it) {
                EstadoLocalizacion.PERMISO_DENEGADO -> {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
                }
                EstadoLocalizacion.PERMISO_APROBADO -> {
                    viewModel.chequearProviderLocalizacion()
                }
                EstadoLocalizacion.PROVEDOR_DENEGADO -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("¿Activar servicio de localización?")
                        .setMessage("Para usar su ubicacion actual, debe activar Ubicación en Ajustes.")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar") { _, _ ->
                            finish()
                        }
                        .setPositiveButton("Ajustes") { _, _ ->
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivityForResult(intent, 0)
                        }
                        .show()
                }
                EstadoLocalizacion.PROVEDOR_APROBADO -> {
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.chequearProviderLocalizacion()
    }
}