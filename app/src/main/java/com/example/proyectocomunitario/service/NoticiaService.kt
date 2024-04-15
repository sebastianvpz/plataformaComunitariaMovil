package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Noticia
import retrofit2.Call
import retrofit2.http.*

interface NoticiaService {

    @GET("/noticia/listar")
    fun obtenerNoticias(): Call<List<Noticia>>

}