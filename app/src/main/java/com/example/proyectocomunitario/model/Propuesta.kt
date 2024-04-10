package com.example.proyectocomunitario.model

data class Propuesta(
    val id: Long,
    val usuario: Usuario,
    val img: String,
    val titulo: String,
    val ubicacion: String,
    val descripcion: String
)
