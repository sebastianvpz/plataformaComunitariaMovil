package com.example.proyectocomunitario.ui.fragments.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Noticia

class NoticiasAdapter : ListAdapter<Noticia, NoticiasAdapter.NoticiaViewHolder>(NoticiaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noticias, parent, false)
        return NoticiaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val currentNoticia = getItem(position)
        holder.bind(currentNoticia)
    }

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.newsTitle)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.newsDescription)
        private val fechaTextView: TextView = itemView.findViewById(R.id.newsDate)
        private val imagenImageView: ImageView = itemView.findViewById(R.id.newsImage)

        fun bind(noticia: Noticia) {
            tituloTextView.text = noticia.titulo
            descripcionTextView.text = noticia.descripcion
            fechaTextView.text = noticia.fechaHora
            // Decodificar la imagen de Base64
            val decodedString: ByteArray = Base64.decode(noticia.img, Base64.DEFAULT)
            val decodedBitmap: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            // Establecer la imagen decodificada en el ImageView
            imagenImageView.setImageBitmap(decodedBitmap)
        }
    }

    private class NoticiaDiffCallback : DiffUtil.ItemCallback<Noticia>() {
        override fun areItemsTheSame(oldItem: Noticia, newItem: Noticia): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Noticia, newItem: Noticia): Boolean {
            return oldItem == newItem
        }
    }
}
