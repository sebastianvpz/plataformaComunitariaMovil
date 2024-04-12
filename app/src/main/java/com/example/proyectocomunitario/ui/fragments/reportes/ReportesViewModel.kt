package com.example.proyectocomunitario.ui.fragments.reportes

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.ReporteGeneral
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.ReporteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportesViewModel : ViewModel() {
    private val reporteService = RetrofitClient.obtenerRetrofit().create(ReporteService::class.java)

    private val _reportes = MutableLiveData<List<ReporteGeneral>>()
    val reportes: LiveData<List<ReporteGeneral>> = _reportes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun obtenerReportes(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("user_id", "")
        if (!usuarioId.isNullOrEmpty()) {
            reporteService.obtenerReportesPorUsuario(usuarioId.toLong(), "Bearer $token").enqueue(object : Callback<List<ReporteGeneral>> {
                override fun onResponse(call: Call<List<ReporteGeneral>>, response: Response<List<ReporteGeneral>>) {
                    if (response.isSuccessful) {
                        _reportes.value = response.body()
                    } else {
                        _error.value = "Error al obtener los reportes: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<List<ReporteGeneral>>, t: Throwable) {
                    _error.value = "Error de red: ${t.message}"
                }
            })
        } else {
            _error.value = "No se pudo obtener el ID de usuario"
        }
    }

    fun eliminarReporte(context: Context, reporteId: Long, tipoReporte: String, token: String) {
        reporteService.eliminarReporte(token = "Bearer $token" ,reporteId, tipoReporte.lowercase()).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    obtenerReportes(context, token) // Actualizar la lista de reportes después de eliminar uno
                } else {
                    _error.value = "Error al eliminar el reporte: ${response.code()}"
                    Log.e("ReportesViewModel", "Error al eliminar el reporte: ${response.code()}")

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error de red: ${t.message}"
            }
        })
    }

}
