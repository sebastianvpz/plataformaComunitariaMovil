package com.example.proyectocomunitario.ui.fragments.events

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.model.ParticipacionEvento
import com.example.proyectocomunitario.model.UsuarioId
import com.example.proyectocomunitario.service.EventoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventosViewModel(private val eventoService: EventoService) : ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    private val _eventos = MutableLiveData<List<Evento>>()
    val eventos: LiveData<List<Evento>> = _eventos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _participacionExitosa = MutableLiveData<Boolean>()
    val participacionExitosa: LiveData<Boolean> = _participacionExitosa

    fun cargarEventos(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "")

        if (!token.isNullOrEmpty()) {
            Log.d("EventosViewModel", "Obteniendo eventos...")
            eventoService.obtenerEventos("Bearer $token").enqueue(object : Callback<List<Evento>> {
                override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                    if (response.isSuccessful) {
                        val eventosResponse = response.body()
                        eventosResponse?.let {
                            _eventos.value = it
                        }
                    } else {
                        _error.value = "Error al obtener los eventos: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                    _error.value = "Error de red: ${t.message}"
                }
            })
        } else {
            _error.value = "Token JWT vacío."
            Log.i("EventosViewModel", "Token JWT vacío.")
        }
    }

    fun asistirEvento(context: Context, evento: Evento, token: String) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val usuarioId = obtenerUsuarioId()
        Log.d("ParticipacionEventoVM", "ID usuario: $usuarioId")
        if (usuarioId != -1L) {
            val participacionEvento = ParticipacionEvento(
                id = -1, // No es necesario especificar el ID al crear una nueva participación
                usuario = UsuarioId(usuarioId) , // Usar el ID del SharedPreferences
                evento = evento,
                rol = "Asistente"
            )

            Log.d("ParticipacionEventoVM", "Asistiendo al evento...")
            eventoService.guardarParticipacionEvento("Bearer $token", participacionEvento).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        _participacionExitosa.value = true
                    } else {
                        _error.value = "Error al asistir al evento: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    _error.value = "Error de red: ${t.message}"
                }
            })
        } else {
            _error.value = "No se pudo obtener el ID de usuario."
            Log.i("ParticipacionEventoVM", "No se pudo obtener el ID de usuario.")
        }
    }

    private fun obtenerUsuarioId(): Long {
        return sharedPreferences.getString("user_id", "")?.toLongOrNull() ?: -1L
    }
}
