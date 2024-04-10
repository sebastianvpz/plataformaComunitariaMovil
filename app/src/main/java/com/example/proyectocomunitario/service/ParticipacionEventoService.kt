package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header


interface ParticipacionEventoService {

    @GET("/peventos/usuario/{usuarioId}")
    fun obtenerParticipacionesPorUsuarioId(
        @Header("Authorization") token: String,
        @Path("usuarioId") usuarioId: Long
    ): Call<List<ParticipacionEvento>>
}