package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.data.model.ClimaDiario
import com.dsl.clima.databinding.PronosticoExtendidoItemBinding

class PronosticoExtendidoAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<ClimaDiario,
            PronosticoExtendidoAdapter.PronosticoExtendidoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PronosticoExtendidoViewHolder {
        return PronosticoExtendidoViewHolder(PronosticoExtendidoItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PronosticoExtendidoViewHolder, position: Int) {
        val climaDiario = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(climaDiario)
        }
        holder.bind(climaDiario)
    }

    class PronosticoExtendidoViewHolder(private var binding: PronosticoExtendidoItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(climaDiario: ClimaDiario) {
            binding.climaDiario = climaDiario
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ClimaDiario>() {
        override fun areItemsTheSame(oldItem: ClimaDiario, newItem: ClimaDiario): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ClimaDiario, newItem: ClimaDiario): Boolean {
            return oldItem.fecha == newItem.fecha
        }
    }

    class OnClickListener(val clickListener: (climaDiario: ClimaDiario) -> Unit) {
        fun onClick(climaDiario: ClimaDiario) = clickListener(climaDiario)
    }
}