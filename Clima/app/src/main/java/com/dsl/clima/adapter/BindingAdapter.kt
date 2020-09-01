package com.dsl.clima.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsl.clima.data.Ciudad
import com.dsl.clima.util.*

@BindingAdapter("listaPronosticos")
fun bindRecyclerViewPronostico(recyclerView: RecyclerView, data: List<Ciudad>?) {
    val adapter = recyclerView.adapter as MisUbicacionesAdapter
    adapter.submitList(data)
}

@BindingAdapter("unirCiudad", "unirPais")
fun bindUnirCiudadPais(text: TextView, ciudad: String?, pais: String?) {
    text.text = ciudad?.let { pais?.let { it1 -> unirCiudadPais(it, it1) } }
}

@BindingAdapter("convertirTemperatura")
fun bindConvertirTemperatura(text: TextView, temperatura: Double) {
    text.text = convertirKelvinCelsius(temperatura)
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

@BindingAdapter("imageUrl")
fun bindImageUrl(imgView: ImageView, imgUrl: String?) {
    val imgUri = "http://openweathermap.org/img/wn/${imgUrl}@2x.png"
    Glide.with(imgView).load(imgUri).into(imgView)
}