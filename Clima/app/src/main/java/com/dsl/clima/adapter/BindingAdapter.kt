package com.dsl.clima.adapter

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dsl.clima.R
import com.dsl.clima.data.Ciudad
import com.dsl.clima.util.convertirKelvinCelsius

@BindingAdapter("listaPronosticos")
fun bindRecyclerViewPronostico(recyclerView: RecyclerView, data: List<Ciudad>?) {
    val adapter = recyclerView.adapter as MisUbicacionesAdapter
    adapter.submitList(data)
}

@BindingAdapter("cambiarColor")
fun bindCambiarColor(tarjeta: LinearLayout, condicion: String) {
    when(condicion) {
        "Dia Despejado" -> tarjeta.setBackgroundResource(R.drawable.degradado_celeste)
        "Dia Nublado" -> tarjeta.setBackgroundResource(R.drawable.degradado_gris)
        "Tarde Despejado" -> tarjeta.setBackgroundResource(R.drawable.degradado_naranja)
        "Tarde Nublado" -> tarjeta.setBackgroundResource(R.drawable.degradado_gris)
        "Noche Despejado" -> tarjeta.setBackgroundResource(R.drawable.degradado_azul)
        "Tarde Nublado" -> tarjeta.setBackgroundResource(R.drawable.degradado_gris)
    }
}

@BindingAdapter("convertirTemperatura")
fun bindConvertirTemperatura(text: TextView, temperatura: Double) {
    text.text = convertirKelvinCelsius(temperatura)
}

@BindingAdapter("imagenUrl")
fun bindImagenUrl(imgView: ImageView, imgUrl: String) {
    val imgUri = "http://openweathermap.org/img/wn/$imgUrl@2x.png"
    Glide.with(imgView.context)
        .load(imgUri)
        .apply(
            RequestOptions()
            .placeholder(R.drawable.animacion_cargando)
            .error(R.drawable.ic_error_imagen))
        .into(imgView)
}