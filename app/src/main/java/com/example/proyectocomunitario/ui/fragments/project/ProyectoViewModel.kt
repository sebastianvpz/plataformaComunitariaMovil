package com.example.proyectocomunitario.ui.fragments.project

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.Proyecto
import com.example.proyectocomunitario.model.SharedPreferencesManager
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.ProyectoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProyectoViewModel(private val proyectoService: ProyectoService, private val context: Context) : ViewModel() {

    private val _proyectos = MutableLiveData<List<Proyecto>>()
    val proyectos: LiveData<List<Proyecto>> = _proyectos

    init {
        obtenerProyectos()
    }

    private fun obtenerProyectos() {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (token.isNullOrEmpty()) {
            Log.e("ProyectoViewModel", "No se encontró el token de autorización en SharedPreferences")
            return
        }
        Log.d("ProyectoViewModel", "Token de autorización encontrado en SharedPreferences: $token")


        val retrofit = RetrofitClient.obtenerRetrofit()
        val proyectoService = retrofit.create(ProyectoService::class.java)

        proyectoService.obtenerTodosLosProyectos("Bearer $token").enqueue(object : Callback<List<Proyecto>> {
            override fun onResponse(call: Call<List<Proyecto>>, response: Response<List<Proyecto>>) {
                if (response.isSuccessful) {
                    val proyectosRecibidos = response.body()
                    _proyectos.value = proyectosRecibidos
                    Log.d("ProyectoViewModel", "Lista de proyectos obtenida correctamente: $proyectosRecibidos")
                    proyectosRecibidos?.forEachIndexed { index, proyecto ->
                        Log.d("ProyectoViewModel", "Proyecto $index: $proyecto")
                    }


                } else {
                    Log.e("ProyectoViewModel", "Error en la respuesta del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Proyecto>>, t: Throwable) {
                Log.e("ProyectoViewModel", "Error en la llamada al servicio: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ProyectoViewModel"
    }
}
