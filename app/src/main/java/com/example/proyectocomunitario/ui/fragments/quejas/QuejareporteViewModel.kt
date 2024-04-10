package com.example.proyectocomunitario.ui.fragments.quejas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.ReporteQuejaDTO
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.QuejaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuejareporteViewModel : ViewModel() {

    private val reporteQuejaService: QuejaService = RetrofitClient.obtenerRetrofit().create(QuejaService::class.java)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _reporteQuejaResponse = MutableLiveData<Unit>()
    val reporteQuejaResponse: LiveData<Unit> = _reporteQuejaResponse

    fun crearReporte(context: Context, reporte: ReporteQuejaDTO, token: String) {
        reporteQuejaService.crearReporte(reporte, "Bearer $token").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _reporteQuejaResponse.value = Unit
                } else {
                    _error.value = "Error al crear reporte de queja: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error de red al crear reporte de queja: ${t.message}"
            }
        })
    }
}
