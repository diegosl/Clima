package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.databinding.MisUbicacionesItemBinding
import com.dsl.clima.domain.model.PronosticoActualModel

class MisUbicacionesAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<PronosticoActualModel,
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
        fun bind(pronosticoActualModel: PronosticoActualModel) {
            binding.pronosticoActualModel = pronosticoActualModel
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PronosticoActualModel>() {
        override fun areItemsTheSame(oldItem: PronosticoActualModel, newItem: PronosticoActualModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PronosticoActualModel, newItem: PronosticoActualModel): Boolean {
            return oldItem.fechaActual == newItem.fechaActual
        }
    }

    class OnClickListener(val clickListener: (pronosticoActualModel: PronosticoActualModel) -> Unit) {
        fun onClick(pronosticoActualModel: PronosticoActualModel) = clickListener(pronosticoActualModel)
    }
}