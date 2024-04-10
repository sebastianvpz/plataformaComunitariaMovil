package com.example.proyectocomunitario.model

data class ReporteEvento(
    val id: Long? = null,
    val fechaReporte: String,
    val mensaje: String,
    val tipoReporte: String? = null, // Opcional, depende de cómo lo manejes en el frontend
    val usuarioId: Long, // ID del usuario que realiza el reporte
    val eventoId: Long // ID del evento reportado
)

