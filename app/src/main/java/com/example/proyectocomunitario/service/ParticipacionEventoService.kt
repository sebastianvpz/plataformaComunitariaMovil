package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header


interface ParticipacionEventoService {

    @GET("/peventos/usuario/{usuarioId}")
    fun obtenerParticipacionesPorUsuarioId(
        @Header("Authorization") token: String,
        @Path("usuarioId") usuarioId: Long
    ): Call<List<ParticipacionEvento>>

    @DELETE("/peventos/cancelar-participacion/{usuarioId}/{eventoId}")
    fun cancelarParticipacion(
        @Header("Authorization") token: String,
        @Path("usuarioId") usuarioId: Long,
        @Path("eventoId") eventoId: Long
    ): Call<Void>
}