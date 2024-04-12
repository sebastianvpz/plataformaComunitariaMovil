package com.example.proyectocomunitario.service

import com.example.proyectocomunitario.model.Queja
import com.example.proyectocomunitario.model.ReporteQuejaDTO
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface QuejaService {

    @GET("/quejas/listar")
    fun obtenerTodasLasQuejas(@Header("Authorization") token: String): Call<List<Queja>>

    @POST("/reportes-queja/crear")
    fun crearReporte(@Body reporte: ReporteQuejaDTO, @Header("Authorization") token: String): Call<Void>

    @POST("/quejas/guardar")
    @Multipart
    fun guardarQueja(
        @Part img: MultipartBody.Part,
        @Part titulo: MultipartBody.Part,
        @Part descripcion: MultipartBody.Part,
        @Part ubicacion: MultipartBody.Part,
        @Part fechaReporte: MultipartBody.Part,
        @Part estado: MultipartBody.Part,
        @Part usuarioId: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<Void>


}
