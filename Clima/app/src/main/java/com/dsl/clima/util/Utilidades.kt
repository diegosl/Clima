package com.dsl.clima.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dsl.clima.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.math.roundToInt

enum class EstadoApi { CARGANDO, ERROR, FINALIZADO }

fun convertirKelvinCelsius(kelvin: Double) = "${(kelvin-273.15).roundToInt()}°"

fun convertirPorcentaje(porcentaje: Double) = "${porcentaje.roundToInt()} %"

fun convertirVelocidad(velocidad: Double) = "$velocidad m/s"

@SuppressLint("ResourceAsColor")
fun efectoShimmer(estadoApi: EstadoApi, shimmerView: ShimmerFrameLayout, view: View, swipeRefreshLayout: SwipeRefreshLayout) {
    val animatorAlpha: ObjectAnimator
    val animatorSetAlpha = AnimatorSet()

    when(estadoApi) {
        EstadoApi.CARGANDO -> {
            shimmerView.startShimmerAnimation()
            shimmerView.visibility = View.VISIBLE
            view.visibility = View.GONE
        }
        EstadoApi.ERROR -> {
            /*Snackbar.make(view, "Comprobar la conexión de red", Snackbar.LENGTH_LONG)
                .setBackgroundTint(R.color.colorPurple)
                .show()*/
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            view.visibility = View.GONE
        }
        EstadoApi.FINALIZADO -> {
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            view.visibility = View.VISIBLE

            animatorAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f)
            animatorAlpha.duration = 2000
            animatorSetAlpha.play(animatorAlpha)
            animatorSetAlpha.start()
        }
    }
}