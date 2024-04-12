package com.example.proyectocomunitario.ui.fragments.reportes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectocomunitario.databinding.FragmentReportesBinding
import com.example.proyectocomunitario.model.ReporteGeneral

class ReportesFragment : Fragment() {

    private var _binding: FragmentReportesBinding? = null
    private val binding get() = _binding!!

    private lateinit var reporteViewModel: ReportesViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var reporteAdapter: ReportesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportesBinding.inflate(inflater, container, false)
        val view = binding.root

        reporteViewModel = ViewModelProvider(this).get(ReportesViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "") ?: ""
        Log.d("ReportesFragment","EL token es: $token")

        reporteAdapter = ReportesAdapter(object : ReportesAdapter.OnReporteClickListener {
            override fun onDeleteClick(reporte: ReporteGeneral) {
                eliminarReporte(reporte)
            }
        })

        binding.recyclerViewReportes.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewReportes.adapter = reporteAdapter

        reporteViewModel.reportes.observe(viewLifecycleOwner, Observer { reportes ->
            reporteAdapter.submitList(reportes)
        })

        reporteViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        reporteViewModel.obtenerReportes(requireContext(), token)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun eliminarReporte(reporte: ReporteGeneral) {
        sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "") ?: ""
        Log.d("ReportesFragment","EL token de delete es: $token")
        reporteViewModel.eliminarReporte(requireContext(), reporte.id, reporte.tipoReporte.lowercase(), token)
    }
}
