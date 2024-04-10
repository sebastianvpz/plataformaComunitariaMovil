package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.CambioContrasenaRequest
import com.example.proyectocomunitario.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {
    @GET("/usuario/obtenerUsuarioPorId/{id}")
    fun obtenerUsuarioPorId(@Path("id") id: Long, @Header("Authorization") token: String): Call<Usuario>

    @PUT("/usuario/modificar/{id}")
    fun modificarUsuario(@Header("Authorization") token: String, @Path("id") id: Long, @Body usuario: Usuario): Call<Usuario>

    @PUT("/usuario/cambiar-contraseña/{id}")
    fun cambiarContraseña(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body cambioContrasenaRequest: CambioContrasenaRequest
    ): Call<Usuario>


}
