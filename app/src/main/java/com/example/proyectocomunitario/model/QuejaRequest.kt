
package com.example.proyectocomunitario.model

data class QuejaRequest(
    val img: String,
    val titulo: String,
    val descripcion: String,
    val ubicacion: String,
    val fechaReporte: String,
    val estado: String,
    val usuarioId: Long
)

