package com.example.proyectocomunitario.ui.fragments.quejas

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Queja
import com.bumptech.glide.Glide


class QuejaAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<QuejaAdapter.QuejaViewHolder>() {

    private var quejas: List<Queja> = emptyList()

    interface OnItemClickListener {
        fun onReportQuejaClick(idQueja: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuejaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_quejas, parent, false)
        return QuejaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuejaViewHolder, position: Int) {
        val currentItem = quejas[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = quejas.size

    fun actualizarQuejas(nuevaListaQuejas: List<Queja>) {
        quejas = nuevaListaQuejas
        notifyDataSetChanged()
    }

    inner class QuejaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quejasImage: ImageView = itemView.findViewById(R.id.quejasImage)
        private val quejasTitle: TextView = itemView.findViewById(R.id.quejasTitle)
        private val quejasDescription: TextView = itemView.findViewById(R.id.quejasDescription)
        private val quejasLocation: TextView = itemView.findViewById(R.id.quejasLocation)
        private val quejasDate: TextView = itemView.findViewById(R.id.quejasDate)
        private val quejasUsername: TextView = itemView.findViewById(R.id.quejasUsername)
        private val btnReportQueja: Button = itemView.findViewById(R.id.btnReportQueja)

        init {
            btnReportQueja.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onReportQuejaClick(quejas[position].id)
                }
            }
        }


        fun bind(queja: Queja) {
            if (!queja.img.isNullOrEmpty()) {
                val decodedString: ByteArray = Base64.decode(queja.img, Base64.DEFAULT)
                Glide.with(itemView)
                    .load(decodedString)
                    .into(quejasImage)
            } else {
                quejasImage.setImageResource(R.drawable.ic_menu_camera)
            }

            // Set the rest of the queja fields
            quejasTitle.text = queja.titulo
            quejasDescription.text = queja.descripcion
            quejasLocation.text = queja.ubicacion
            quejasDate.text = queja.fechaReporte
            quejasUsername.text = queja.username
        }
    }
}