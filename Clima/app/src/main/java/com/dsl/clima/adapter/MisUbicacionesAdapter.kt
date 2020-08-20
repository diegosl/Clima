package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.data.Ciudad
import com.dsl.clima.data.Pronostico
import com.dsl.clima.databinding.GridViewMisUbicacionesItemBinding

class MisUbicacionesAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<Ciudad,
            MisUbicacionesAdapter.MisUbicacionesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisUbicacionesViewHolder {
        return MisUbicacionesViewHolder(GridViewMisUbicacionesItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MisUbicacionesViewHolder, position: Int) {
        val ciudad = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(ciudad)
        }
        holder.bind(ciudad)
    }

    class MisUbicacionesViewHolder(private var binding:
                                   GridViewMisUbicacionesItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ciudad: Ciudad) {
            binding.ciudad = ciudad
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ciudad>() {
        override fun areItemsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (ciudad: Ciudad) -> Unit) {
        fun onClick(ciudad: Ciudad) = clickListener(ciudad)
    }
}