package com.example.proyectocomunitario.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Noticia
import com.example.proyectocomunitario.network.RetrofitClient

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var noticiasAdapter: NoticiasAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewNews)

        // Inicializar el ViewModel
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(RetrofitClient.noticiaService))
            .get(HomeViewModel::class.java)

        // Inicializar el RecyclerView y el adaptador
        noticiasAdapter = NoticiasAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noticiasAdapter
        }

        // Observar los cambios en la lista de noticias
        homeViewModel.noticias.observe(viewLifecycleOwner, Observer {
            noticiasAdapter.submitList(it)
        })

        // Observar los errores
        homeViewModel.error.observe(viewLifecycleOwner, Observer {
            // Manejar errores si es necesario
        })

        // Cargar las noticias al iniciar
        homeViewModel.cargarNoticias()

        return root
    }

    fun onNoticiaClicked(noticia: Noticia) {
    }
}
