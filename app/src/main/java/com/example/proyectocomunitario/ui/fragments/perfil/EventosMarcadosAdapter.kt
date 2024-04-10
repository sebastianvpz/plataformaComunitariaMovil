package com.example.proyectocomunitario.ui.fragments.perfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento

class EventosMarcadosAdapter(
    private val onItemClick: (Evento) -> Unit
) : ListAdapter<Evento, EventosMarcadosAdapter.EventoViewHolder>(EventoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_peventos, parent, false)
        return EventoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val currentEvento = getItem(position)
        holder.bind(currentEvento)
        holder.itemView.setOnClickListener { onItemClick(currentEvento) }
    }

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.txtEventoTitulo)
        private val fechaLugarTextView: TextView = itemView.findViewById(R.id.txtEventoFechaLugar)
        private val desenmarcarButton: Button = itemView.findViewById(R.id.btnDesenmarcar)

        fun bind(evento: Evento) {
            tituloTextView.text = evento.titulo
            fechaLugarTextView.text = "${evento.fechaHora} - ${evento.ubicacion}"
            // Set click listener for desenmarcarButton if needed
        }
    }

    private class EventoDiffCallback : DiffUtil.ItemCallback<Evento>() {
        override fun areItemsTheSame(oldItem: Evento, newItem: Evento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Evento, newItem: Evento): Boolean {
            return oldItem == newItem
        }
    }
}