package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento
import com.example.proyectocomunitario.model.ReporteEvento
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @Multipart
    @POST("/eventos/guardar")
    fun guardarEvento(
        @Part img: MultipartBody.Part,
        @Part titulo: MultipartBody.Part,
        @Part descripcion: MultipartBody.Part,
        @Part fechaHora: MultipartBody.Part,
        @Part ubicacion: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<Void>
}
