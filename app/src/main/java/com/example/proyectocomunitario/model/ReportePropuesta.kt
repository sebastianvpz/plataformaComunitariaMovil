package com.example.proyectocomunitario.model

data class ReportePropuesta(
    val fechaReporte: String,
    val mensaje: String,
    val tipoReporte: String,
    val usuarioId: Long,
    val propuestaId: Long
)
