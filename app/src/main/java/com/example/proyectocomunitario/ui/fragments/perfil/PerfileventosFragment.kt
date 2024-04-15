package com.example.proyectocomunitario.ui.fragments.perfil

import android.content.Context
import android.content.SharedPreferences
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

    private lateinit var sharedPreferences: SharedPreferences
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfileventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("user_id", "")

        eventosAdapter = EventosMarcadosAdapter(
            onItemClick = { /* handle item click if needed */ },
            onDesenmarcarClick = { eventoId ->
                desenmarcarEvento(eventoId)
            }
        )
        binding.recyclerViewEventosMarcados.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventosAdapter
        }

        viewModel = ViewModelProvider(this).get(PerfileventosViewModel::class.java)
        viewModel.eventosList.observe(viewLifecycleOwner, Observer { eventos ->
            eventosAdapter.submitList(eventos)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Log.i("PerfilEventosFragment", "Error: $error")
        })

        cargarParticipaciones()
    }

    private fun cargarParticipaciones() {
        viewModel.cargarParticipacionesPorUsuario(requireContext())

    }

    private fun desenmarcarEvento(eventoId: Long) {

        if (!userId.isNullOrEmpty()){
            viewModel.eliminarParticipacionEvento(requireContext(), userId!!.toLong(), eventoId)
        }
    }
}