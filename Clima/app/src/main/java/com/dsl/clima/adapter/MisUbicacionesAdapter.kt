package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.databinding.MisUbicacionesItemBinding
import com.dsl.clima.domain.model.PronosticoModel

class MisUbicacionesAdapter(private val onClickListener: OnClickListener, private val onLongClickListener: OnLongClickListener) :
    ListAdapter<PronosticoModel,
            MisUbicacionesAdapter.MisUbicacionesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisUbicacionesViewHolder {
        return MisUbicacionesViewHolder(MisUbicacionesItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MisUbicacionesViewHolder, position: Int) {
        val pronosticoModel = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pronosticoModel)
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener.onLongClick(pronosticoModel)
            true
        }
        holder.bind(pronosticoModel)
    }

    class MisUbicacionesViewHolder(private var binding: MisUbicacionesItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pronosticoModel: PronosticoModel) {
            binding.pronosticoModel = pronosticoModel
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PronosticoModel>() {
        override fun areItemsTheSame(oldItem: PronosticoModel, newItem: PronosticoModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PronosticoModel, newItem: PronosticoModel): Boolean {
            return oldItem.ciudadModel.nombreCiudad == newItem.ciudadModel.nombreCiudad
        }
    }

    class OnClickListener(val clickListener: (pronosticoModel: PronosticoModel) -> Unit) {
        fun onClick(pronosticoModel: PronosticoModel) = clickListener(pronosticoModel)
    }

    class OnLongClickListener(val longClickListener: (pronosticoModel: PronosticoModel) -> Unit) {
        fun onLongClick(pronosticoModel: PronosticoModel) = longClickListener(pronosticoModel)
    }
}