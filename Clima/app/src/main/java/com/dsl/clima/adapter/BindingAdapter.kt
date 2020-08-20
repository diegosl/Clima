package com.dsl.clima.adapter

import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.R
import com.dsl.clima.data.Ciudad
import com.dsl.clima.data.Pronostico

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