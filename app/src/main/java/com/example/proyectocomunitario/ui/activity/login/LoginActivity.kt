package com.example.proyectocomunitario.ui.activity.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.proyectocomunitario.MainActivity
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.AuthenticationResponse
import com.example.proyectocomunitario.model.Credenciales
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.ui.activity.register.RegistroActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val tokenKey = "jwt_token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegistro = findViewById<TextView>(R.id.txtRegistro)

        btnLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            val credenciales = Credenciales(username, password)

            val call: Call<AuthenticationResponse> = RetrofitClient.authService.login(credenciales)
            call.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                    if (response.isSuccessful) {
                        val authenticationResponse = response.body()

                        Log.d("LoginActivity", "Token recibido: ${authenticationResponse?.token}")

                        // Guardar el JWT en SharedPreferences
                        sharedPreferences.edit().putString(tokenKey, authenticationResponse?.token).apply()

                        // Extraer el ID del usuario del token JWT y guardarlo en SharedPreferences
                        val userId = extractUserIdFromToken(authenticationResponse?.token)
                        sharedPreferences.edit().putString("user_id", userId).apply()
                        Log.d("LoginActivity", "Id del usuario guardado en Shared Preferences: $userId")

                        // Iniciar la actividad principal (MainActivity)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Cerrar la actividad de login.
                    } else {
                        showError("Error en el inicio de sesión. Por favor, verifica tus credenciales e inténtalo nuevamente.")
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Log.e("LoginActivity", "Error en la conexión", t)
                    showError("Error en la conexión. Por favor, verifica tu conexión a internet e inténtalo nuevamente.")
                }
            })
        }

        txtRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        findViewById<EditText>(R.id.etUsername).text.clear()
        findViewById<EditText>(R.id.etPassword).text.clear()
    }


    fun extractUserIdFromToken(token: String?): String? {
        // Verificar si el token es nulo o vacío
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

    fun decodeBase64(input: String): ByteArray {
        return android.util.Base64.decode(input, android.util.Base64.URL_SAFE)
    }

    fun extractUserIdFromJson(jsonPayload: String): String? {
        // Parsear el JSON y extraer el ID del usuario
        // Este es un ejemplo, debes adaptarlo a la estructura real de tu payload
        val jsonObject = org.json.JSONObject(jsonPayload)
        return jsonObject.optString("userId")
    }

}