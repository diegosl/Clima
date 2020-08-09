package com.dsl.clima.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsl.clima.data.Pronostico
import com.dsl.clima.databinding.GridViewMisUbicacionesItemBinding

class MisUbicacionesAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<Pronostico,
            MisUbicacionesAdapter.MisUbicacionesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisUbicacionesViewHolder {
        return MisUbicacionesViewHolder(GridViewMisUbicacionesItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MisUbicacionesViewHolder, position: Int) {
        val pronostico = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pronostico)
        }
        holder.bind(pronostico)
    }

    class MisUbicacionesViewHolder(private var binding:
                                   GridViewMisUbicacionesItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pronostico: Pronostico) {
            binding.pronostico = pronostico
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pronostico>() {
        override fun areItemsTheSame(oldItem: Pronostico, newItem: Pronostico): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pronostico, newItem: Pronostico): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (pronostico: Pronostico) -> Unit) {
        fun onClick(pronostico: Pronostico) = clickListener(pronostico)
    }
}