package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.databinding.PronosticoExtendidoItemBinding
import com.dsl.clima.domain.model.PronosticoDiarioModel

class PronosticoExtendidoAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<PronosticoDiarioModel,
            PronosticoExtendidoAdapter.PronosticoExtendidoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PronosticoExtendidoViewHolder {
        return PronosticoExtendidoViewHolder(PronosticoExtendidoItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PronosticoExtendidoViewHolder, position: Int) {
        val pronosticoDiarioModel = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pronosticoDiarioModel)
        }
        holder.bind(pronosticoDiarioModel)
    }

    class PronosticoExtendidoViewHolder(private var binding: PronosticoExtendidoItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pronosticoDiarioModel: PronosticoDiarioModel) {
            binding.pronosticoDiarioModel = pronosticoDiarioModel
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PronosticoDiarioModel>() {
        override fun areItemsTheSame(oldItem: PronosticoDiarioModel, newItem: PronosticoDiarioModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PronosticoDiarioModel, newItem: PronosticoDiarioModel): Boolean {
            return oldItem.fechaDiaria == newItem.fechaDiaria
        }
    }

    class OnClickListener(val clickListener: (pronosticoDiarioModel: PronosticoDiarioModel) -> Unit) {
        fun onClick(pronosticoDiarioModel: PronosticoDiarioModel) = clickListener(pronosticoDiarioModel)
    }
}