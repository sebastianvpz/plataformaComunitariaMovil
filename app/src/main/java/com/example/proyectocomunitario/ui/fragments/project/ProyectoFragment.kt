package com.example.proyectocomunitario.ui.fragments.project

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.ui.fragments.project.ProyectoAdapter
import com.example.proyectocomunitario.ui.fragments.project.ProyectoViewModel
import com.example.proyectocomunitario.service.ProyectoService

class ProyectoFragment : Fragment() {

    private lateinit var proyectoAdapter: ProyectoAdapter
    private lateinit var proyectoViewModel: ProyectoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_proyecto, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerViewProyectos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        proyectoAdapter = ProyectoAdapter(emptyList())
        recyclerView.adapter = proyectoAdapter

        Log.d("ProyectoFragment", "RecyclerView configurado correctamente")

        val proyectoService = RetrofitClient.proyectoService
        val viewModelFactory = ProyectoViewModelFactory(proyectoService, requireContext())
        proyectoViewModel = ViewModelProvider(this, viewModelFactory).get(ProyectoViewModel::class.java)

        proyectoViewModel.proyectos.observe(viewLifecycleOwner, Observer { proyectos ->
            proyectoAdapter.actualizarProyectos(proyectos)
            Log.d("ProyectoFragment", "Lista de proyectos actualizada: $proyectos")
        })

        return rootView
    }
}