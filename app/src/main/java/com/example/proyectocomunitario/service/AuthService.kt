package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.AuthenticationResponse
import com.example.proyectocomunitario.model.Credenciales
import com.example.proyectocomunitario.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/login")
    fun login(@Body credenciales: Credenciales): Call<AuthenticationResponse>

    @POST("/api/auth/register")
    fun register(@Body usuario: Usuario): Call<AuthenticationResponse>
}