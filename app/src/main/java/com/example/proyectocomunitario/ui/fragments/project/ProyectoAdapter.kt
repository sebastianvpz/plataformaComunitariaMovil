package com.example.proyectocomunitario.ui.fragments.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Proyecto
import android.util.Base64
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bumptech.glide.Glide


class ProyectoAdapter(private var proyectos: List<Proyecto>) : RecyclerView.Adapter<ProyectoAdapter.ProyectoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyectoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_proyecto, parent, false)
        return ProyectoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProyectoViewHolder, position: Int) {
        val proyecto = proyectos[position]
        holder.bind(proyecto)
    }

    override fun getItemCount(): Int {
        return proyectos.size
    }

    fun actualizarProyectos(nuevosProyectos: List<Proyecto>) {
        proyectos = nuevosProyectos
        notifyDataSetChanged()
    }

    class ProyectoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.proyectoTitle)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.proyectoDescription)
        private val ubicacionTextView: TextView = itemView.findViewById(R.id.proyectoUbicacion)
        private val estadoTextView: TextView = itemView.findViewById(R.id.proyectoEstado)
        private val imagenImageView: ImageView = itemView.findViewById(R.id.proyectoImage)

        fun bind(proyecto: Proyecto) {
            tituloTextView.text = proyecto.titulo
            descripcionTextView.text = proyecto.descripcion
            ubicacionTextView.text = proyecto.ubicacion
            estadoTextView.text = proyecto.estado

            val decodedString: ByteArray? = Base64.decode(proyecto.img, Base64.DEFAULT)

            if (decodedString != null) {
                Glide.with(itemView)
                    .load(decodedString)
                    .into(imagenImageView)
            } else {
                Log.e("ProyectoAdapter", "La imagen Base64 está vacía o no se pudo decodificar")
            }
        }

        private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }
    }
}

