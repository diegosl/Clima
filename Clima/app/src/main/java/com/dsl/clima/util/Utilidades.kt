package com.dsl.clima.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dsl.clima.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

enum class EstadoApi { CARGANDO, ERROR, FINALIZADO }

fun convertirKelvinCelsius(kelvin: Double) = "${(kelvin-273.15).roundToInt()}°"

@SuppressLint("ResourceAsColor")
fun efectoShimmer(estadoApi: EstadoApi, shimmerView: ShimmerFrameLayout, linearLayout: LinearLayout, swipeRefreshLayout: SwipeRefreshLayout) {
    when(estadoApi) {
        EstadoApi.CARGANDO -> {
            shimmerView.startShimmerAnimation()
            shimmerView.visibility = View.VISIBLE
            linearLayout.visibility = View.GONE
        }
        EstadoApi.ERROR -> {
            /*Snackbar.make(linearLayout, "Comprobar la conexión de red", Snackbar.LENGTH_LONG)
                .setBackgroundTint(R.color.colorPurple)
                .show()*/
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            linearLayout.visibility = View.GONE
        }
        EstadoApi.FINALIZADO -> {
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            linearLayout.visibility = View.VISIBLE
        }
    }
}