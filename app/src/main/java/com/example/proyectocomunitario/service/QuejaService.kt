package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Queja
import com.example.proyectocomunitario.model.ReporteQuejaDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QuejaService {

    @GET("/quejas/listar")
    fun obtenerTodasLasQuejas(@Header("Authorization") token: String): Call<List<Queja>>

    @POST("/reportes-queja/crear")
    fun crearReporte(@Body reporte: ReporteQuejaDTO, @Header("Authorization") token: String): Call<Void>

}
