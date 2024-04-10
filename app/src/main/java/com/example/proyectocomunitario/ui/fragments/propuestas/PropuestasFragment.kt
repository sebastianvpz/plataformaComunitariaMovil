package com.example.proyectocomunitario.ui.fragments.propuestas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Propuesta
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.PropuestaService

class PropuestasFragment : Fragment() {

    private lateinit var viewModel: PropuestasViewModel
    private var propuestasAdapter: PropuestasAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_propuestas, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPropuestas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        propuestasAdapter = PropuestasAdapter(
            { propuesta -> onItemClick(propuesta) },
            { propuesta -> onLikeButtonClick(propuesta) },
            { propuesta -> onDislikeButtonClick(propuesta) },
            { propuestaId -> onReportButtonClick(propuestaId) }
        )
        recyclerView.adapter = propuestasAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel setup
        val propuestaService = RetrofitClient.propuestaService
        val factory = PropuestasViewModelFactory(propuestaService)
        viewModel = ViewModelProvider(this, factory).get(PropuestasViewModel::class.java)

        // Observe propuestas list from ViewModel
        viewModel.propuestas.observe(viewLifecycleOwner, Observer { propuestas ->
            propuestasAdapter?.submitList(propuestas)
        })

        // Observe errors
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        // Load propuestas
        viewModel.loadPropuestas(requireContext())
    }

    private fun onItemClick(propuesta: Propuesta) {
        // Handle item click, if needed
    }

    private fun onLikeButtonClick(propuesta: Propuesta) {
        // Handle like button click
        Toast.makeText(requireContext(), "Liked: ${propuesta.titulo}", Toast.LENGTH_SHORT).show()
    }

    private fun onDislikeButtonClick(propuesta: Propuesta) {
        // Handle dislike button click
        Toast.makeText(requireContext(), "Disliked: ${propuesta.titulo}", Toast.LENGTH_SHORT).show()
    }

    private fun onReportButtonClick(propuestaId: Long) {
        // Navegar al fragmento de reporte con el ID de la propuesta
        Log.d("PropuestasFragment", "ID de la propuesta seleccionada: $propuestaId")
        val bundle = Bundle().apply {
            putLong("propuestaId", propuestaId)
        }
        findNavController().navigate(R.id.nav_propuestaReporte, bundle)

    }

}

