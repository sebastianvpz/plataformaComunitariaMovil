package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Propuesta
import com.example.proyectocomunitario.model.ReportePropuesta
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PropuestaService {

    @GET("/propuestas/listar")
    fun obtenerPropuestas(@Header("Authorization") token: String): Call<List<Propuesta>>

    @POST("/reportes-propuestas/agregar")
    fun agregarReportePropuesta(@Header("Authorization") token: String, @Body reportePropuestaDTO: ReportePropuesta): Call<ReportePropuesta>
}
