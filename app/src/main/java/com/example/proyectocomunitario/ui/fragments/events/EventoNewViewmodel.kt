package com.example.proyectocomunitario.ui.fragments.events

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.EventoRequest
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.EventoService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventoNewViewModel : ViewModel() {
    private val eventoService = RetrofitClient.obtenerRetrofit().create(EventoService::class.java)

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun guardarEvento(context: Context, eventoRequest: EventoRequest) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (token.isNullOrEmpty()) {
            _error.value = "No se pudo obtener el token de usuario"
            return
        }

        val imgPart = MultipartBody.Part.createFormData("img", null, eventoRequest.img.toRequestBody("text/plain".toMediaTypeOrNull()))
        val tituloPart = MultipartBody.Part.createFormData("titulo", null, eventoRequest.titulo.toRequestBody("text/plain".toMediaTypeOrNull()))
        val descripcionPart = MultipartBody.Part.createFormData("descripcion", null, eventoRequest.descripcion.toRequestBody("text/plain".toMediaTypeOrNull()))
        val fechaHoraPart = MultipartBody.Part.createFormData("fechaHora", null, eventoRequest.fechaHora.toRequestBody("text/plain".toMediaTypeOrNull()))
        val ubicacionPart = MultipartBody.Part.createFormData("ubicacion", null, eventoRequest.ubicacion.toRequestBody("text/plain".toMediaTypeOrNull()))

        eventoService.guardarEvento(
            imgPart,
            tituloPart,
            descripcionPart,
            fechaHoraPart,
            ubicacionPart,
            "Bearer $token"
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _mensaje.value = "Evento guardado exitosamente"
                } else {
                    _error.value = "Error al guardar el evento: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error de red: ${t.message}"
            }
        })
    }
}
