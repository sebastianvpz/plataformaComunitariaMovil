package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Propuesta
import com.example.proyectocomunitario.model.ReportePropuesta
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PropuestaService {

    @GET("/propuestas/listar")
    fun obtenerPropuestas(@Header("Authorization") token: String): Call<List<Propuesta>>

    @POST("/reportes-propuestas/agregar")
    fun agregarReportePropuesta(@Header("Authorization") token: String, @Body reportePropuestaDTO: ReportePropuesta): Call<ReportePropuesta>

    @POST("/propuestas/guardar")
    @Multipart
    fun guardarPropuesta(
        @Part img: MultipartBody.Part,
        @Part titulo: MultipartBody.Part,
        @Part descripcion: MultipartBody.Part,
        @Part ubicacion: MultipartBody.Part,
        @Part usuarioId: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<Void>
}
