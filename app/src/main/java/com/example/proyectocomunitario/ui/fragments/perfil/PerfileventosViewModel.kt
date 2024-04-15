package com.example.proyectocomunitario.ui.fragments.perfil

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento
import com.example.proyectocomunitario.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfileventosViewModel : ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    private val _eventosList = MutableLiveData<List<Evento>>()
    val eventosList: LiveData<List<Evento>> = _eventosList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarParticipacionesPorUsuario(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        val token = sharedPreferences.getString("jwt_token", "")

        if (!userId.isNullOrEmpty() && !token.isNullOrEmpty()) {
            Log.d("PerfilEventosViewModel", "Obteniendo participaciones del usuario...")
            RetrofitClient.participacionEventoService.obtenerParticipacionesPorUsuarioId("Bearer $token", userId.toLong()).enqueue(object : Callback<List<ParticipacionEvento>> {
                override fun onResponse(call: Call<List<ParticipacionEvento>>, response: Response<List<ParticipacionEvento>>) {
                    if (response.isSuccessful) {
                        val participaciones = response.body()
                        participaciones?.let {
                            // Mapear las participaciones a eventos
                            val eventos = participaciones.map { it.evento }
                            _eventosList.value = eventos
                            Log.d("PerfilEventosViewModel", "Eventos cargados correctamente: $eventos")
                        }
                    } else {
                        _error.value = "Error al cargar las participaciones del usuario: ${response.message()}"
                        Log.e("PerfilEventosViewModel", "Error al cargar las participaciones del usuario: ${response.message()}")
                        // Agrega un log para imprimir toda la respuesta
                        Log.e("PerfilEventosViewModel", "Respuesta de red completa: ${response}")

                    }
                }

                override fun onFailure(call: Call<List<ParticipacionEvento>>, t: Throwable) {
                    _error.value = "Error al cargar las participaciones del usuario: ${t.message}"
                    Log.e("PerfilEventosViewModel", "Error al cargar las participaciones del usuario: ${t.message}")

                }
            })
        } else {
            _error.value = "ID de usuario o token JWT vacíos."
            Log.e("PerfilEventosViewModel", "ID de usuario o token JWT vacíos.")

        }
    }

    fun eliminarParticipacionEvento(context: Context, usuarioId: Long, eventoId: Long) {
        val token = sharedPreferences.getString("jwt_token", "")

        if (!token.isNullOrEmpty()) {
            Log.d("PerfilEventosViewModel", "Eliminando participación del evento...")
            RetrofitClient.participacionEventoService.cancelarParticipacion("Bearer $token", usuarioId, eventoId).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("PerfilEventosViewModel", "Participación eliminada correctamente")
                        // Llamar al método cargarParticipacionesPorUsuario para actualizar la lista de eventos
                        cargarParticipacionesPorUsuario(context)
                    } else {
                        _error.value = "Error al eliminar la participación del evento: ${response.message()}"
                        Log.e("PerfilEventosViewModel", "Error al eliminar la participación del evento: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _error.value = "Error de red al eliminar la participación del evento: ${t.message}"
                    Log.e("PerfilEventosViewModel", "Error de red al eliminar la participación del evento: ${t.message}")
                }
            })
        } else {
            _error.value = "Token JWT vacío."
            Log.e("PerfilEventosViewModel", "Token JWT vacío.")
        }
    }

}
