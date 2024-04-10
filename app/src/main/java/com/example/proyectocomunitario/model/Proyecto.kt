package com.example.proyectocomunitario.model


import android.util.Base64

data class Proyecto(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val ubicacion: String,
    val estado: String,
    val img: String // La imagen en formato Base64
)