package com.example.proyectocomunitario.model

data class CambioContrasenaRequest(
    val contrasenaAntigua: String,
    val nuevaContrasena: String
)
