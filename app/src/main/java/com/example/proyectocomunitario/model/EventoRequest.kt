package com.example.proyectocomunitario.model

data class EventoRequest(
    val img : String,
    val titulo: String,
    val descripcion: String,
    val fechaHora: String,
    val ubicacion: String
)
