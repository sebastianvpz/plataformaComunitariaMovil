package com.example.proyectocomunitario.network

import com.example.proyectocomunitario.service.AuthService
import com.example.proyectocomunitario.service.EventoService
import com.example.proyectocomunitario.service.ParticipacionEventoService
import com.example.proyectocomunitario.service.PropuestaService
import com.example.proyectocomunitario.service.ProyectoService
import com.example.proyectocomunitario.service.QuejaService
import com.example.proyectocomunitario.service.UsuarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8090" // URL de tu servidor local

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun obtenerRetrofit(): Retrofit {
        return retrofit
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
    val proyectoService: ProyectoService by lazy {
        retrofit.create(ProyectoService::class.java)
    }
    val quejaService: QuejaService by lazy {
        retrofit.create(QuejaService::class.java)
    }
    val usuarioService: UsuarioService by  lazy {
        retrofit.create(UsuarioService::class.java)
    }
    val participacionEventoService: ParticipacionEventoService by lazy {
        retrofit.create(ParticipacionEventoService::class.java)
    }
    val eventoService: EventoService by lazy {
        retrofit.create(EventoService:: class.java)
    }
    val propuestaService : PropuestaService by  lazy {
        retrofit.create(PropuestaService::class.java)
    }
}
