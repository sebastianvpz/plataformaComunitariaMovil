package com.example.proyectocomunitario.ui.fragments.reportes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.databinding.ItemReportesBinding
import com.example.proyectocomunitario.model.ReporteGeneral

class ReportesAdapter(private val onDeleteClickListener: OnReporteClickListener) :
    ListAdapter<ReporteGeneral, ReportesAdapter.ReporteViewHolder>(ReporteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteViewHolder {
        val binding =
            ItemReportesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReporteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReporteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ReporteViewHolder(private val binding: ItemReportesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnDeleteReport.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val reporte = getItem(position)
                    onDeleteClickListener.onDeleteClick(reporte)
                }
            }
        }

        fun bind(reporte: ReporteGeneral) {
            binding.apply {
                reportType.text = reporte.tipoReporte
                reportMensaje.text = reporte.mensaje
                reportFecha.text = reporte.fechaReporte
            }
        }
    }

    interface OnReporteClickListener {
        fun onDeleteClick(reporte: ReporteGeneral)
    }

    class ReporteDiffCallback : DiffUtil.ItemCallback<ReporteGeneral>() {
        override fun areItemsTheSame(oldItem: ReporteGeneral, newItem: ReporteGeneral) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ReporteGeneral, newItem: ReporteGeneral) =
            oldItem == newItem
    }
}

