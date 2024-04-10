package com.example.proyectocomunitario.ui.fragments.quejas

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.databinding.FragmentQuejareporteBinding
import com.example.proyectocomunitario.model.ReporteQuejaDTO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuejareporteFragment : Fragment() {

    private var _binding: FragmentQuejareporteBinding? = null
    private val binding get() = _binding!!

    private lateinit var quejareporteViewModel: QuejareporteViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var quejaId: Long = -1 // Valor predeterminado

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuejareporteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener el token de SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "") ?: ""

        // Obtener el ID de la queja pasado como argumento
        arguments?.let {
            quejaId = it.getLong("quejaId", -1)
        }

        quejareporteViewModel = ViewModelProvider(this).get(QuejareporteViewModel::class.java)

        binding.btnPublicarQueja.setOnClickListener {
            val mensaje = binding.editMensajeQueja.text.toString().trim()

            if (mensaje.isNotEmpty()) {
                val usuarioId = sharedPreferences.getString("user_id", "")

                if (!usuarioId.isNullOrEmpty() && quejaId != -1L) {
                    val fechaReporte = obtenerFechaActual()
                    val reporteQuejaDTO = ReporteQuejaDTO(
                        mensaje = mensaje,
                        usuarioId = usuarioId.toLong(),
                        quejaId = quejaId,
                        fechaReporte = fechaReporte,
                        tipoReporte = "QUEJA"

                    )

                    quejareporteViewModel.crearReporte(requireContext(), reporteQuejaDTO, token)
                } else {
                    Toast.makeText(requireContext(), "Error al obtener ID de usuario o ID de queja", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor ingresa una descripción del reporte", Toast.LENGTH_SHORT).show()
            }
        }

        quejareporteViewModel.reporteQuejaResponse.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Reporte de queja creado exitosamente", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar la navegación de regreso si es necesario
        })

        quejareporteViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerFechaActual(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formato.format(Date())
    }
}