package com.example.proyectocomunitario.ui.fragments.perfil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectocomunitario.databinding.FragmentPerfileventosBinding

class PerfilEventosFragment : Fragment() {

    private lateinit var binding: FragmentPerfileventosBinding
    private lateinit var viewModel: PerfileventosViewModel
    private lateinit var eventosAdapter: EventosMarcadosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfileventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el adaptador del RecyclerView
        eventosAdapter = EventosMarcadosAdapter { /* handle item click if needed */ }
        binding.recyclerViewEventosMarcados.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventosAdapter
        }

        // Inicializar y observar el ViewModel
        viewModel = ViewModelProvider(this).get(PerfileventosViewModel::class.java)
        viewModel.eventosList.observe(viewLifecycleOwner, Observer { eventos ->
            Log.i("PerfilEventosFragment", "Eventos list updated: $eventos")
            eventosAdapter.submitList(eventos)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Log.i("PerfilEventosFragment", "Error: $error")

            // Handle error if needed
        })

        viewModel.cargarParticipacionesPorUsuario(requireContext())
    }
}