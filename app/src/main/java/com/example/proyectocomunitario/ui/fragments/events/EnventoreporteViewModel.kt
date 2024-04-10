package com.example.proyectocomunitario.ui.fragments.events

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.ReporteEvento
import com.example.proyectocomunitario.service.EventoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EnventoreporteViewModel(private val reporteEventoService: EventoService) : ViewModel() {

    private val _reporteExitoso = MutableLiveData<Boolean>()
    val reporteExitoso: LiveData<Boolean> = _reporteExitoso

    fun reportarEvento(context: Context, eventoId: Long, mensaje: String) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        val token = sharedPreferences.getString("jwt_token", "")

        if (!userId.isNullOrEmpty() && !token.isNullOrEmpty()) {
            val currentDate = Calendar.getInstance().time
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate)

            val reporteEvento = ReporteEvento(
                fechaReporte = formattedDate,
                mensaje = mensaje,
                tipoReporte = "EVENTO", // Tipo de reporte como String
                usuarioId = userId.toLong(),
                eventoId = eventoId
            )

            reporteEventoService.crearReporteEvento("Bearer $token", reporteEvento)
                .enqueue(object : Callback<ReporteEvento> {
                    override fun onResponse(call: Call<ReporteEvento>, response: Response<ReporteEvento>) {
                        if (response.isSuccessful) {
                            _reporteExitoso.value = true // Reporte exitoso
                        }
                    }

                    override fun onFailure(call: Call<ReporteEvento>, t: Throwable) {
                        // Manejar error de red si es necesario
                    }
                })
        } else {
            // Manejar caso de usuario no autenticado o token no disponible
        }
    }
}
