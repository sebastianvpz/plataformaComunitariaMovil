package com.example.proyectocomunitario.ui.fragments.propuestas

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.Propuesta
import com.example.proyectocomunitario.service.PropuestaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropuestasViewModel(private val propuestaService: PropuestaService) : ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    private val _propuestas = MutableLiveData<List<Propuesta>>()
    val propuestas: LiveData<List<Propuesta>> = _propuestas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _likeSuccess = MutableLiveData<Boolean>()
    val likeSuccess: LiveData<Boolean> = _likeSuccess

    private val _dislikeSuccess = MutableLiveData<Boolean>()
    val dislikeSuccess: LiveData<Boolean> = _dislikeSuccess

    fun cargarPropuestas(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (!token.isNullOrEmpty()) {
            Log.d("PropuestasViewModel", "Obteniendo propuestas...")
            propuestaService.obtenerPropuestas("Bearer $token").enqueue(object : Callback<List<Propuesta>> {
                override fun onResponse(call: Call<List<Propuesta>>, response: Response<List<Propuesta>>) {
                    if (response.isSuccessful) {
                        val propuestasResponse = response.body()
                        propuestasResponse?.let {
                            _propuestas.value = it
                        }
                    } else {
                        _error.value = "Error al obtener las propuestas: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<List<Propuesta>>, t: Throwable) {
                    _error.value = "Error de red: ${t.message}"
                }
            })
        } else {
            _error.value = "Token JWT vacío."
            Log.i("PropuestasViewModel", "Token JWT vacío.")
        }
    }

    fun likePropuesta(context: Context, propuesta: Propuesta, token: String) {
        // Lógica para enviar el "me gusta" de la propuesta al servicio
    }

    fun dislikePropuesta(context: Context, propuesta: Propuesta, token: String) {
        // Lógica para enviar el "no me gusta" de la propuesta al servicio
    }
    fun loadPropuestas(context: Context) {
        cargarPropuestas(context) // Llamar al método existente
    }

}
