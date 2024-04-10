package com.example.proyectocomunitario.ui.fragments.quejas

import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.service.QuejaService
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectocomunitario.model.Queja
import com.example.proyectocomunitario.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuejasViewModel(private val context: Context) : ViewModel() {

    private val _quejas = MutableLiveData<List<Queja>>()
    val quejas: LiveData<List<Queja>> = _quejas

    init {
        obtenerQuejas()
    }

    private fun obtenerQuejas() {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (token.isNullOrEmpty()) {
            Log.e(TAG, "No se encontró el token de autorización en SharedPreferences")
            return
        }
        Log.d(TAG, "Token de autorización encontrado en SharedPreferences: $token")

        val retrofit = RetrofitClient.obtenerRetrofit()
        val quejaService = retrofit.create(QuejaService::class.java)

        quejaService.obtenerTodasLasQuejas("Bearer $token").enqueue(object : Callback<List<Queja>> {
            override fun onResponse(call: Call<List<Queja>>, response: Response<List<Queja>>) {
                if (response.isSuccessful) {
                    val quejasRecibidas = response.body()
                    _quejas.value = quejasRecibidas
                    Log.d(TAG, "Lista de quejas obtenida correctamente: $quejasRecibidas")
                } else {
                    Log.e(TAG, "Error en la respuesta del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Queja>>, t: Throwable) {
                Log.e(TAG, "Error en la llamada al servicio: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "QuejasViewModel"
    }
}