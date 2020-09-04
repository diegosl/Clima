package com.dsl.clima.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

enum class EstadoApi { CARGANDO, ERROR, FINALIZADO }

fun unirCiudadPais(ciudad: String, pais: String) = "$ciudad $pais"

fun convertirGrado(temperatura: Double) = "${temperatura.roundToInt()}°"

fun convertirPorcentaje(porcentaje: Double) = "${porcentaje.roundToInt()}%"

fun convertirVelocidad(velocidad: Double) = "$velocidad m/s"

fun convertirFecha(tiempo: Int): String {
    val fecha = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    return fecha.format(tiempo*1000L)
}

fun convertirTempMinMax(tempMin: Double, tempMax: Double) = "${tempMin.roundToInt()}°/${tempMax.roundToInt()}°"

fun convertirDia(tiempo: Int): String {
    val calendario = Calendar.getInstance()
    calendario.timeInMillis = tiempo * 1000L
    return when(calendario.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Domingo"
        Calendar.MONDAY -> "Lunes"
        Calendar.TUESDAY -> "Martes"
        Calendar.WEDNESDAY -> "Miércoles"
        Calendar.THURSDAY -> "Jueves"
        Calendar.FRIDAY -> "Viernes"
        Calendar.SATURDAY -> "Sábado"
        else -> ""
    }
}

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
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            view.visibility = View.VISIBLE

            animatorAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f)
            animatorAlpha.duration = 1000
            animatorSetAlpha.play(animatorAlpha)
            animatorSetAlpha.start()

            Snackbar.make(view, "Comprobar la conexión de red", Snackbar.LENGTH_LONG).show()
        }
        EstadoApi.FINALIZADO -> {
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            view.visibility = View.VISIBLE

            animatorAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f)
            animatorAlpha.duration = 1000
            animatorSetAlpha.play(animatorAlpha)
            animatorSetAlpha.start()
        }
    }
}