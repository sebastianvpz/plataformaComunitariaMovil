package com.example.proyectocomunitario.model

data class PropuestaRequest(

    val img: String,
    val titulo: String,
    val descripcion: String,
    val ubicacion: String,
    val usuarioId : Long
)
