package com.example.proyectocomunitario.ui.activity.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.MainActivity
import com.example.proyectocomunitario.model.Usuario
import com.example.proyectocomunitario.network.RetrofitClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.proyectocomunitario.model.AuthenticationResponse
import com.example.proyectocomunitario.ui.activity.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val loginRedirectText = findViewById<TextView>(R.id.loginRedirectText)

        btnRegistro.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etRegNombre).text.toString()
            val apellidos = findViewById<EditText>(R.id.etRegApellidos).text.toString()
            val email = findViewById<EditText>(R.id.etRegEmail).text.toString()
            val username = findViewById<EditText>(R.id.etRegUsername).text.toString()
            val password = findViewById<EditText>(R.id.etRegPassword).text.toString()

            val usuario = Usuario(nombre, apellidos, email, username, password)

            val call: Call<AuthenticationResponse> = RetrofitClient.authService.register(usuario)
            call.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                    if (response.isSuccessful) {
                        val jwt = response.body()?.token
                        Log.d("LoginActivity", "Token recibido: ${jwt}")

                        val userId = extractUserIdFromToken(jwt)
                        Log.d("LoginActivity", "Id recibido: ${userId}")

                        saveJwtAndUserIdToSharedPreferences(jwt, userId)

                        val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Cerrar la actividad de registro después de un registro exitoso
                    } else {
                        showError("Error en el registro. Por favor, verifica tus datos e inténtalo nuevamente.")
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Log.e("RegistroActivity", "Error en la conexión", t)
                    showError("Error en la conexión. Por favor, verifica tu conexión a internet e inténtalo nuevamente.")
                }
            })
        }

        loginRedirectText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun extractUserIdFromToken(token: String?): String? {
        if (token.isNullOrEmpty()) return null
        try {
            // Dividir el token en sus partes (header, payload y firma)
            val parts = token.split(".")
            if (parts.size == 3) {
                // Decodificar el payload (parte central del token)
                val payload = decodeBase64(parts[1])

                // Parsear el JSON del payload
                val jsonPayload = String(payload, Charsets.UTF_8)

                // Extraer el ID del usuario del JSON del payload
                val userId = extractUserIdFromJson(jsonPayload)

                return userId
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun saveJwtAndUserIdToSharedPreferences(jwt: String?, userId: String?) {
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("JWT", jwt).apply()
        sharedPreferences.edit().putString("userId", userId).apply()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun decodeBase64(input: String): ByteArray {
        return android.util.Base64.decode(input, android.util.Base64.URL_SAFE)
    }

    fun extractUserIdFromJson(jsonPayload: String): String? {
        // Parsear el JSON y extraer el ID del usuario
        val jsonObject = org.json.JSONObject(jsonPayload)
        return jsonObject.optString("userId")
    }
}
