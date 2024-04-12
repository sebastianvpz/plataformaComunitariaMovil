package com.example.proyectocomunitario.ui.fragments.propuestas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.PropuestaRequest
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.PropuestaService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ProuestanuevaViewModel : ViewModel() {
    private val propuestaService = RetrofitClient.obtenerRetrofit().create(PropuestaService::class.java)

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun guardarPropuesta(context: Context, propuesta: PropuestaRequest) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (token.isNullOrEmpty()) {
            _error.value = "No se pudo obtener el token de usuario"
            return
        }

        val imgPart = MultipartBody.Part.createFormData("img", null, propuesta.img.toRequestBody("text/plain".toMediaTypeOrNull()))
        val tituloPart = MultipartBody.Part.createFormData("titulo", null, propuesta.titulo.toRequestBody("text/plain".toMediaTypeOrNull()))
        val descripcionPart = MultipartBody.Part.createFormData("descripcion", null, propuesta.descripcion.toRequestBody("text/plain".toMediaTypeOrNull()))
        val ubicacionPart = MultipartBody.Part.createFormData("ubicacion", null, propuesta.ubicacion.toRequestBody("text/plain".toMediaTypeOrNull()))
        val usuarioIdPart = MultipartBody.Part.createFormData("usuarioId", null, propuesta.usuarioId.toString().toRequestBody("text/plain".toMediaTypeOrNull()))

        propuestaService.guardarPropuesta(
            imgPart,
            tituloPart,
            descripcionPart,
            ubicacionPart,
            usuarioIdPart,
            "Bearer $token"
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _mensaje.value = "Propuesta guardada exitosamente"
                } else {
                    _error.value = "Error al guardar la propuesta: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error de red: ${t.message}"
            }
        })
    }}