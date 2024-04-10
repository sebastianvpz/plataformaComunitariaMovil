package com.example.proyectocomunitario.ui.fragments.propuestas

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Propuesta

class PropuestasAdapter(
    private val onItemClick: (Propuesta) -> Unit,
    private val onLikeButtonClick: (Propuesta) -> Unit,
    private val onDislikeButtonClick: (Propuesta) -> Unit,
    private val onReportButtonClick: (Long) -> Unit
) : ListAdapter<Propuesta, PropuestasAdapter.PropuestaViewHolder>(PropuestaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropuestaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_propuestas, parent, false)
        return PropuestaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PropuestaViewHolder, position: Int) {
        val currentPropuesta = getItem(position)
        holder.bind(currentPropuesta)
        holder.itemView.setOnClickListener { onItemClick(currentPropuesta) }
        holder.likeButton.setOnClickListener { onLikeButtonClick(currentPropuesta) }
        holder.dislikeButton.setOnClickListener { onDislikeButtonClick(currentPropuesta) }
        holder.reportButton.setOnClickListener { onReportButtonClick(currentPropuesta.id) }
    }

    class PropuestaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.propuestaTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.propuestaDescription)
        private val locationTextView: TextView = itemView.findViewById(R.id.propuestaLocation)
        private val imageView: ImageView = itemView.findViewById(R.id.propuestaImage)
        val likeButton: Button = itemView.findViewById(R.id.btnLike)
        val dislikeButton: Button = itemView.findViewById(R.id.btnDislike)
        val reportButton: Button = itemView.findViewById(R.id.btnReportPropuesta)

        fun bind(propuesta: Propuesta) {
            titleTextView.text = propuesta.titulo
            descriptionTextView.text = propuesta.descripcion
            locationTextView.text = propuesta.ubicacion

            // Decodificar la imagen en base64 y establecerla en el ImageView
            val decodedImageBytes = Base64.decode(propuesta.img, Base64.DEFAULT)
            val decodedImageBitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
            imageView.setImageBitmap(decodedImageBitmap)
        }
    }

    private class PropuestaDiffCallback : DiffUtil.ItemCallback<Propuesta>() {
        override fun areItemsTheSame(oldItem: Propuesta, newItem: Propuesta): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Propuesta, newItem: Propuesta): Boolean {
            return oldItem == newItem
        }
    }
}

