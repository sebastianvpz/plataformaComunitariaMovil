package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento
import com.example.proyectocomunitario.model.ReporteEvento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EventoService {

    @GET("/eventos/listar")
    fun obtenerEventos(@Header("Authorization") token: String): Call<List<Evento>>

    @POST("/reportes-eventos/crear")
    fun crearReporteEvento(
        @Header("Authorization") token: String,
        @Body reporteEvento: ReporteEvento
    ): Call<ReporteEvento>

    @POST("/peventos/guardar")
    fun guardarParticipacionEvento(
        @Header("Authorization") token: String,
        @Body participacionEvento: ParticipacionEvento
    ): Call<Any>
}
