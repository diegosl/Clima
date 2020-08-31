package com.dsl.clima.adapter

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dsl.clima.R
import com.dsl.clima.data.Ciudad
import com.dsl.clima.util.convertirKelvinCelsius
import com.dsl.clima.util.convertirPorcentaje
import com.dsl.clima.util.convertirVelocidad
import java.text.DateFormat

@BindingAdapter("listaPronosticos")
fun bindRecyclerViewPronostico(recyclerView: RecyclerView, data: List<Ciudad>?) {
    val adapter = recyclerView.adapter as MisUbicacionesAdapter
    adapter.submitList(data)
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

@BindingAdapter("imagenUrl")
fun bindImagenUrl(imgView: ImageView, imgUrl: String) {
    val imgUri = "http://openweathermap.org/img/wn/${imgUrl}@2x.png"
    Glide.with(imgView.context).load(imgUri).into(imgView)
}