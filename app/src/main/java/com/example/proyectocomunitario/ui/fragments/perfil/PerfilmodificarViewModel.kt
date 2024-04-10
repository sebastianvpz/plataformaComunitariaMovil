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

class PerfilmodificarViewModel : ViewModel() {
    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario>
        get() = _usuario

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private lateinit var sharedPreferences: SharedPreferences


    fun obtenerDatosUsuario(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        val token = sharedPreferences.getString("jwt_token", "")
        if (!userId.isNullOrEmpty() && !token.isNullOrEmpty()) {
            Log.d("PerfilModificarViewModel", "Obteniendo datos del usuario...")
            RetrofitClient.usuarioService.obtenerUsuarioPorId(userId.toLong(),"Bearer $token")
                .enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            Log.d("PerfilModificarViewModel", "Datos del usuario obtenidos correctamente: $user")
                            _usuario.value = user
                        } else {
                            Log.e("PerfilModificarViewModel", "Error en la respuesta del servidor: ${response.message()}")
                            _error.value = "Error en la respuesta del servidor: ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Log.e("PerfilModificarViewModel", "Error en la conexión: ${t.message}")
                        _error.value = "Error en la conexión: ${t.message}"
                    }
                })
        } else {
            Log.e("PerfilModificarViewModel", "ID de usuario o token no válidos")
            _error.value = "ID de usuario o token no válidos"
        }
    }

    fun modificarUsuario(context: Context, usuario: Usuario) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        val token = sharedPreferences.getString("jwt_token", "")
        if (!userId.isNullOrEmpty()) {
            Log.d("PerfilModificarViewModel", "Modificando datos del usuario...")
            RetrofitClient.usuarioService.modificarUsuario("Bearer $token", userId.toLong(), usuario)
                .enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            val modifiedUser = response.body()
                            Log.d("PerfilModificarViewModel", "Usuario modificado correctamente: $modifiedUser")
                            _usuario.value = modifiedUser
                        } else {
                            Log.e("PerfilModificarViewModel", "Error en la respuesta del servidor: ${response.message()}")
                            _error.value = "Error en la respuesta del servidor: ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Log.e("PerfilModificarViewModel", "Error en la conexión: ${t.message}")
                        _error.value = "Error en la conexión: ${t.message}"
                    }
                })
        } else {
            Log.e("PerfilModificarViewModel", "ID de usuario no válido")
            _error.value = "ID de usuario no válido"
        }
    }
}