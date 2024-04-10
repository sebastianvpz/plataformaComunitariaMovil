package com.example.proyectocomunitario.model

data class ParticipacionEvento(
    val id: Long,
    val evento: Evento,
    val usuario: UsuarioId, // Cambiar para usar UsuarioId
    val rol: String
)
