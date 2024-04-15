package com.example.proyectocomunitario.ui.fragments.events

import EventosAdapter
import android.content.Context
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
import com.example.proyectocomunitario.databinding.FragmentEventosBinding
import com.example.proyectocomunitario.model.Evento
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.R


class EventosFragment : Fragment() {

    private lateinit var binding: FragmentEventosBinding
    private lateinit var eventosViewModel: EventosViewModel
    private lateinit var eventosAdapter: EventosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventosBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventosAdapter = EventosAdapter(
            onItemClick = { evento -> onItemClick(evento) },
            onReportButtonClick = { eventoId -> onReportButtonClick(eventoId) },
            onAsistirButtonClick = { evento -> onAsistirButtonClick(evento) }
        )

        binding.recyclerViewEventos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventosAdapter
        }

        val eventoService = RetrofitClient.eventoService
        eventosViewModel = ViewModelProvider(this, EventosViewModelFactory(eventoService)).get(EventosViewModel::class.java)

        eventosViewModel.eventos.observe(viewLifecycleOwner, Observer { eventos ->
            eventosAdapter.submitList(eventos)
        })

        eventosViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            // Handle error if needed
        })

        eventosViewModel.participacionExitosa.observe(viewLifecycleOwner, Observer { exitosa ->
            if (exitosa) {
                // Actualizar la lista de eventos después de la asistencia exitosa
                eventosViewModel.cargarEventos(requireContext())
            }
        })
        binding.btnNuevoEventoView.setOnClickListener {
            // Navegar al fragmento EventoNewFragment
            findNavController().navigate(R.id.eventoNewFragment)
        }


        eventosViewModel.cargarEventos(requireContext())
    }


    private fun onItemClick(evento: Evento) {
        // Handle item click here
    }

    private fun onReportButtonClick(eventoId: Long) {
        // Navegar al fragmento de reporte con el ID del evento
        Log.d("EventosFragment", "ID del evento seleccionado: $eventoId")
        val bundle = Bundle().apply {
            putLong("eventoId", eventoId)
        }
        findNavController().navigate(R.id.nav_eventoreporte, bundle)
    }

    private fun onAsistirButtonClick(evento: Evento) {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", "") ?: ""
        Toast.makeText(requireContext(), "Asistiendo a evento...", Toast.LENGTH_SHORT).show()

        eventosViewModel.asistirEvento(requireContext(), evento, token)

    }


}




