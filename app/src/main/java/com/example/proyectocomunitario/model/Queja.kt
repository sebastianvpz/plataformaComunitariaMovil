package com.example.proyectocomunitario.model

data class Queja(
    val id: Int,
    val username: String,
    val titulo: String,
    val ubicacion: String,
    val descripcion: String,
    val fechaReporte: String,
    val estado: String,
    val img: String
)
