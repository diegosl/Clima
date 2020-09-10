package com.dsl.clima.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsl.clima.domain.model.PronosticoDiarioModel
import com.dsl.clima.domain.model.PronosticoModel
import com.dsl.clima.util.*

@BindingAdapter("listaPronosticos")
fun bindRecyclerViewPronostico(recyclerView: RecyclerView, data: List<PronosticoModel>?) {
    val adapter = recyclerView.adapter as MisUbicacionesAdapter
    adapter.submitList(data)
}

@BindingAdapter("listaPronosticoExtendido")
fun bindRecyclerViewPronosticoExtendido(recyclerView: RecyclerView, data: List<PronosticoDiarioModel>?) {
    val adapter = recyclerView.adapter as PronosticoExtendidoAdapter
    adapter.submitList(data)
}

@BindingAdapter("unirCiudad", "unirPais")
fun bindUnirCiudadPais(text: TextView, ciudad: String?, pais: String?) {
    text.text = ciudad?.let { pais?.let { it1 -> unirCiudadPais(it, it1) } }
}

@BindingAdapter("convertirTemperatura")
fun bindConvertirTemperatura(text: TextView, temperatura: Double) {
    text.text = convertirGrado(temperatura)
}

@BindingAdapter("convertirTemperaturaMin", "convertirTemperaturaMax")
fun bindConvertirTemperaturaMinMax(text: TextView, tempMin: Double?, tempMax: Double?) {
    text.text = tempMin?.let { tempMax?.let { it1 -> convertirTempMinMax(it, it1) } }
}

@BindingAdapter("convertirPorcentaje")
fun bindConvertirPorcentaje(text: TextView, porcentaje: Double) {
    text.text = convertirPorcentaje(porcentaje)
}

@BindingAdapter("convertirVelocidad")
fun bindConvertirVelocidad(text: TextView, velocidad: Double) {
    text.text = convertirVelocidad(velocidad)
}

@BindingAdapter("convertirFecha")
fun bindConvertirFecha(text: TextView, tiempo: Int) {
    text.text = convertirFecha(tiempo)
}

@BindingAdapter("convertirDia")
fun bindConvertirDia(text: TextView, tiempo: Int) {
    text.text = convertirDia(tiempo)
}

@BindingAdapter("imageUrl")
fun bindImageUrl(imgView: ImageView, imgUrl: String?) {
    val imgUri = "http://openweathermap.org/img/wn/${imgUrl}@2x.png"
    Glide.with(imgView).load(imgUri).into(imgView)
}