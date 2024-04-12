package com.example.proyectocomunitario.ui.fragments.quejas

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.QuejaRequest
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.QuejaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class QuejanuevaViewModel : ViewModel() {
    private val quejaService = RetrofitClient.obtenerRetrofit().create(QuejaService::class.java)

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun guardarQueja(context: Context, queja: QuejaRequest) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (token.isNullOrEmpty()) {
            _error.value = "No se pudo obtener el token de usuario"
            return
        }
        

        val imgPart = MultipartBody.Part.createFormData("img", null, queja.img.toRequestBody("text/plain".toMediaTypeOrNull()))
        val tituloPart = MultipartBody.Part.createFormData("titulo", null, queja.titulo.toRequestBody("text/plain".toMediaTypeOrNull()))
        val descripcionPart = MultipartBody.Part.createFormData("descripcion", null, queja.descripcion.toRequestBody("text/plain".toMediaTypeOrNull()))
        val ubicacionPart = MultipartBody.Part.createFormData("ubicacion", null, queja.ubicacion.toRequestBody("text/plain".toMediaTypeOrNull()))
        val fechaReportePart = MultipartBody.Part.createFormData("fechaReporte", null, queja.fechaReporte.toRequestBody("text/plain".toMediaTypeOrNull()))
        val estadoPart = MultipartBody.Part.createFormData("estado", null, queja.estado.toRequestBody("text/plain".toMediaTypeOrNull()))
        val usuarioIdPart = MultipartBody.Part.createFormData("usuarioId", null, queja.usuarioId.toString().toRequestBody("text/plain".toMediaTypeOrNull()))


        quejaService.guardarQueja(
            imgPart,
            tituloPart,
            descripcionPart,
            ubicacionPart,
            fechaReportePart,
            estadoPart,
            usuarioIdPart,
            "Bearer $token"
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _mensaje.value = "Queja guardada exitosamente"
                } else {
                    _error.value = "Error al guardar la queja: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error de red: ${t.message}"
            }
        })
    }
}

