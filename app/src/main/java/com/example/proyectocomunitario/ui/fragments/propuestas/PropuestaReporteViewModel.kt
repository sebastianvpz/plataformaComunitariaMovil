package com.example.proyectocomunitario.ui.fragments.propuestas

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.ReportePropuesta
import com.example.proyectocomunitario.service.PropuestaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropuestaReporteViewModel(private val propuestaService: PropuestaService) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _reportePropuestaResponse = MutableLiveData<ReportePropuesta>()
    val reportePropuestaResponse: LiveData<ReportePropuesta> = _reportePropuestaResponse



    fun reportarPropuesta(context: Context, reportePropuesta: ReportePropuesta) {
        val token = obtainTokenFromSharedPreferences(context)

        token?.let {
            propuestaService.agregarReportePropuesta("Bearer $it", reportePropuesta).enqueue(object : Callback<ReportePropuesta> {
                override fun onResponse(call: Call<ReportePropuesta>, response: Response<ReportePropuesta>) {
                    if (response.isSuccessful) {
                        _reportePropuestaResponse.value = response.body()
                    } else {
                        val errorMessage = try {
                            response.errorBody()?.string()
                        } catch (e: Exception) {
                            "Error al procesar el cuerpo del error"
                        }
                        Log.e("PropuestaReporteViewModel", "Error al reportar propuesta - Código: ${response.code()}, Mensaje: ${response.message()}, Body: $errorMessage")
                        _error.value = "Error al reportar propuesta: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<ReportePropuesta>, t: Throwable) {
                    Log.e("PropuestaReporteViewModel", "Error de red: ${t.message}")
                    _error.value = "Error de red: ${t.message}"
                }
            })

        } ?: run {
            _error.value = "Token JWT no encontrado."
            Log.e("PropuestaReporteViewModel", "Token JWT no encontrado.")
        }
    }

    private fun obtainTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        return sharedPreferences.getString("jwt_token", null)
    }
}
