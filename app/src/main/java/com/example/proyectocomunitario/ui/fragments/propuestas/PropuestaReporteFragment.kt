package com.example.proyectocomunitario.ui.fragments.propuestas

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
import com.example.proyectocomunitario.databinding.FragmentPropuestareporteBinding
import com.example.proyectocomunitario.model.ReportePropuesta
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.ui.fragments.events.EnventoreporteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PropuestaReporteFragment : Fragment() {

    private var _binding: FragmentPropuestareporteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PropuestaReporteViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var propuestaId: Long = -1 // Valor predeterminado

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPropuestareporteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener el ID de usuario de SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)

        // Obtener el ID de la propuesta pasado como argumento
        arguments?.let {
            propuestaId = it.getLong("propuestaId", -1)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reportePropuestaService = RetrofitClient.propuestaService
        val viewModelFactory = PropuestaReporteViewModelFactory(reportePropuestaService)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PropuestaReporteViewModel::class.java)


        binding.btnRealizarReportePRopuesta.setOnClickListener {
            val mensaje = binding.editMensajeReportePropuesta.text.toString().trim()

            if (mensaje.isNotEmpty()) {
                val usuarioId = sharedPreferences.getString("user_id", "-1")?.toLong() ?: -1L

                if (usuarioId != -1L && propuestaId != -1L) {
                    val fechaReporte = obtenerFechaActual()
                    val reportePropuesta = ReportePropuesta(
                        fechaReporte = fechaReporte, // Puedes utilizar la fecha actual aquí
                        mensaje = mensaje,
                        tipoReporte = "PROPUESTA",
                        usuarioId = usuarioId,
                        propuestaId = propuestaId
                    )

                    viewModel.reportarPropuesta(requireContext(), reportePropuesta)
                } else {
                    Toast.makeText(requireContext(), "Error al obtener ID de usuario o ID de propuesta", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor ingresa una descripción del reporte", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.reportePropuestaResponse.observe(viewLifecycleOwner, Observer { reportePropuesta ->
            // Aquí puedes manejar la respuesta del servidor después de reportar la propuesta
            Toast.makeText(requireContext(), "Propuesta reportada correctamente", Toast.LENGTH_SHORT).show()
            // Puedes agregar aquí la navegación de regreso a la pantalla anterior si es necesario
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerFechaActual(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formato.format(Date())
    }
}
