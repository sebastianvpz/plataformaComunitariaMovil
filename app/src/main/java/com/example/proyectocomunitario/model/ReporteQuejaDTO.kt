package com.example.proyectocomunitario.model

data class ReporteQuejaDTO(
    val mensaje: String,
    val fechaReporte: String,
    val tipoReporte: String,
    val usuarioId: Long,
    val quejaId: Long
)
