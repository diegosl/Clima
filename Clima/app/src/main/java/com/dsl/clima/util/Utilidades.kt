package com.dsl.clima.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dsl.clima.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

enum class EstadoApi { CARGANDO, ERROR, FINALIZADO }

enum class EstadoLocalizacion { PERMISO_DENEGADO, PERMISO_APROBADO, PROVEDOR_DENEGADO, PROVEDOR_APROBADO }

fun unirCiudadPais(ciudad: String, pais: String) = "$ciudad $pais"

fun convertirGrado(temperatura: Double) = "${temperatura.roundToInt()}°"

fun convertirPorcentaje(porcentaje: Double) = "${porcentaje.absoluteValue.roundToInt()}%"

fun convertirVelocidad(velocidad: Double) = "$velocidad m/s"

fun convertirFecha(tiempo: Int) = if(tiempo == 0) {
    "--/--/---- --:--:-- --"
}
else {
    val fecha = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a")
    fecha.format(tiempo*1000L)
}

fun convertirTempMinMax(tempMin: Double, tempMax: Double) = "${convertirGrado(tempMin)}/${convertirGrado(tempMax)}"

fun convertirDia(tiempo: Int) = if(tiempo == 0) {
    "--"
}
else {
    val calendario = Calendar.getInstance()
    calendario.timeInMillis = tiempo * 1000L
    when(calendario.get(Calendar.DAY_OF_WEEK)) {
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
fun efectoShimmer(estadoApi: EstadoApi, shimmerView: ShimmerFrameLayout, view: View, swipeRefreshLayout: SwipeRefreshLayout, mensaje: String) {
    val animatorAlpha: ObjectAnimator
    val animatorSetAlpha = AnimatorSet()

    when(estadoApi) {
        EstadoApi.CARGANDO -> {
            shimmerView.startShimmer()
            shimmerView.visibility = View.VISIBLE
            view.visibility = View.GONE
        }
        EstadoApi.ERROR -> {
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmer()
            shimmerView.visibility = View.GONE
            view.visibility = View.VISIBLE

            animatorAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f)
            animatorAlpha.duration = 1000
            animatorSetAlpha.play(animatorAlpha)
            animatorSetAlpha.start()

            mostrarSnackBar(view, mensaje)
        }
        EstadoApi.FINALIZADO -> {
            swipeRefreshLayout.isRefreshing = false
            shimmerView.stopShimmer()
            shimmerView.visibility = View.GONE
            view.visibility = View.VISIBLE

            animatorAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f)
            animatorAlpha.duration = 1000
            animatorSetAlpha.play(animatorAlpha)
            animatorSetAlpha.start()
        }
    }
}

fun mostrarSnackBar(view: View, mensaje: String) {
    Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
        .setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorGrayLight))
        .setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimaryDark))
        .show()
}