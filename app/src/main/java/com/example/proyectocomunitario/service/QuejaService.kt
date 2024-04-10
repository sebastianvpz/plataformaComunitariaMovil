package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Queja
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface QuejaService {

    @GET("/quejas/listar")
    fun obtenerTodasLasQuejas(@Header("Authorization") token: String): Call<List<Queja>>

    // Otros métodos para crear, actualizar, eliminar quejas, etc.
}
