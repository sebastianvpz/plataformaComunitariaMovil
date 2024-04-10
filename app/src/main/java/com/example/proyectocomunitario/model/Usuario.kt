package com.example.proyectocomunitario.model

data class Usuario(
    val nombre: String,
    val apellido: String,
    val email: String,
    val username: String,
    val password: String? = null
)
