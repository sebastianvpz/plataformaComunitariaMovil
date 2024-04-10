package com.example.proyectocomunitario.ui.fragments.perfil

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.Usuario
import com.example.proyectocomunitario.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class PerfilViewModel : ViewModel() {

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario>
        get() = _usuario

    private lateinit var sharedPreferences: SharedPreferences

    fun obtenerDatosUsuario(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        val token = sharedPreferences.getString("jwt_token", "")

        if (!userId.isNullOrEmpty() && !token.isNullOrEmpty()) {
            Log.d("PerfilViewModel", "Obteniendo datos del usuario...")
            RetrofitClient.usuarioService.obtenerUsuarioPorId(userId.toLong(), "Bearer $token")
                .enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            Log.d("PerfilViewModel", "Datos del usuario obtenidos correctamente: $user")
                            _usuario.value = user
                        } else {
                            Log.e("PerfilViewModel", "Error en la respuesta del servidor: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Log.e("PerfilViewModel", "Error en la conexión: ${t.message}")
                    }
                })
        } else {
            Log.e("PerfilViewModel", "ID de usuario o token no válido")
        }
    }
}