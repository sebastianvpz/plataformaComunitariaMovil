package com.example.proyectocomunitario.model

data class Evento(
    val id: Long,
    val titulo: String,
    val descripcion: String,
    val fechaHora: String,
    val ubicacion: String,
    val img: String
)
