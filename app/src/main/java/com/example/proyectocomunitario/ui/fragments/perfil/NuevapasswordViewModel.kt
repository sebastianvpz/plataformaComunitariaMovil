package com.example.proyectocomunitario.ui.fragments.perfil

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.CambioContrasenaRequest
import com.example.proyectocomunitario.model.Usuario
import com.example.proyectocomunitario.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevapasswordViewModel : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _exito = MutableLiveData<Boolean>()
    val exito: LiveData<Boolean>
        get() = _exito

    private lateinit var sharedPreferences: SharedPreferences

    fun cambiarContraseña(context: Context, contrasenaAntigua: String, nuevaContrasena: String) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        val token = sharedPreferences.getString("jwt_token", "")

        if (!userId.isNullOrEmpty() && !token.isNullOrEmpty()) {
            val cambioContrasenaRequest = CambioContrasenaRequest(contrasenaAntigua, nuevaContrasena)

            RetrofitClient.usuarioService.cambiarContraseña("Bearer $token" ,userId.toLong(), cambioContrasenaRequest)
                .enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            Log.d("CambioContrasenaViewModel", "Contraseña cambiada exitosamente")
                            _exito.value = true
                        } else {
                            Log.e("CambioContrasenaViewModel", "Error al cambiar la contraseña: ${response.message()}")
                            _error.value = "Error al cambiar la contraseña: ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Log.e("CambioContrasenaViewModel", "Error en la conexión al cambiar la contraseña: ${t.message}")
                        _error.value = "Error en la conexión al cambiar la contraseña: ${t.message}"
                    }
                })
        } else {
            Log.e("CambioContrasenaViewModel", "ID de usuario o token no válidos")
            _error.value = "ID de usuario o token no válidos"
        }
    }
}