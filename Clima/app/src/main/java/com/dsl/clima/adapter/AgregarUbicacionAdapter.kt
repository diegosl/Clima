package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.databinding.UbicacionItemBinding
import com.dsl.clima.domain.model.CiudadModel

class AgregarUbicacionAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<CiudadModel,
            AgregarUbicacionAdapter.AgregarUbicacionViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgregarUbicacionViewHolder {
        return AgregarUbicacionViewHolder(
            UbicacionItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AgregarUbicacionViewHolder, position: Int) {
        val ciudadModel = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(ciudadModel)
        }
        holder.bind(ciudadModel)
    }

    class AgregarUbicacionViewHolder(private var binding: UbicacionItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ciudadModel: CiudadModel) {
            binding.ciudadModel = ciudadModel
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CiudadModel>() {
        override fun areItemsTheSame(oldItem: CiudadModel, newItem: CiudadModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CiudadModel, newItem: CiudadModel): Boolean {
            return oldItem.nombreCiudad == newItem.nombreCiudad
        }
    }

    class OnClickListener(val clickListener: (ciudadModel: CiudadModel) -> Unit) {
        fun onClick(ciudadModel: CiudadModel) = clickListener(ciudadModel)
    }
}