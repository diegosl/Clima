package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.data.model.DatosMeteorologicosActuales
import com.dsl.clima.databinding.MisUbicacionesItemBinding

class MisUbicacionesAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<DatosMeteorologicosActuales,
            MisUbicacionesAdapter.MisUbicacionesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisUbicacionesViewHolder {
        return MisUbicacionesViewHolder(MisUbicacionesItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MisUbicacionesViewHolder, position: Int) {
        val datosMeteorologicosActuales = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(datosMeteorologicosActuales)
        }
        holder.bind(datosMeteorologicosActuales)
    }

    class MisUbicacionesViewHolder(private var binding: MisUbicacionesItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(datosMeteorologicosActuales: DatosMeteorologicosActuales) {
            binding.datosMeteorologicosActuales = datosMeteorologicosActuales
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DatosMeteorologicosActuales>() {
        override fun areItemsTheSame(oldItem: DatosMeteorologicosActuales, newItem: DatosMeteorologicosActuales): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatosMeteorologicosActuales, newItem: DatosMeteorologicosActuales): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (datosMeteorologicosActuales: DatosMeteorologicosActuales) -> Unit) {
        fun onClick(datosMeteorologicosActuales: DatosMeteorologicosActuales) = clickListener(datosMeteorologicosActuales)
    }
}