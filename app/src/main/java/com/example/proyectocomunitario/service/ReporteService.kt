package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.ReporteGeneral
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReporteService {

    @GET("/reportes/usuario/{usuarioId}")
    fun obtenerReportesPorUsuario(
        @Path("usuarioId") usuarioId: Long,
        @Header("Authorization") token: String
    ): Call<List<ReporteGeneral>>

    @DELETE("/reportes/eliminar/{id}")
    fun eliminarReporte(
        @Header("Authorization") token: String,
        @Path("id") reporteId: Long,
        @Query("tipo") tipoReporte: String
    ): Call<Void>

}