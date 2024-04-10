package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Propuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PropuestaService {

    @GET("/propuestas/listar")
    fun obtenerPropuestas(@Header("Authorization") token: String): Call<List<Propuesta>>
}
