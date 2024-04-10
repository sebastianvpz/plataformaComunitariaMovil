package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Proyecto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProyectoService {
    @GET("/proyectos/listar")
    fun obtenerTodosLosProyectos(@Header("Authorization") token: String): Call<List<Proyecto>>
}