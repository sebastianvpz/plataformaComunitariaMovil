package com.example.proyectocomunitario.ui.fragments.quejas

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.service.QuejaService

class QuejasFragment : Fragment() {

    private lateinit var quejaViewModel: QuejasViewModel
    private lateinit var quejaAdapter: QuejaAdapter

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_quejas, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerViewQuejas)

        sharedPreferences = requireContext().getSharedPreferences("com.example.proyectocomunitario.PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", "") ?: ""

        quejaAdapter = QuejaAdapter(object : QuejaAdapter.OnItemClickListener {
            override fun onReportQuejaClick(idQueja: Long) {
                val bundle = Bundle().apply {
                    putLong("quejaId", idQueja)
                }
                Log.d("QuejasFragment", "ID de la queja seleccionada: $idQueja")
                findNavController().navigate(R.id.quejareporteFragment,bundle)
            }
        })
        recyclerView.adapter = quejaAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val quejaService = RetrofitClient.obtenerRetrofit().create(QuejaService::class.java)

        quejaViewModel = ViewModelProvider(this, QuejaViewModelFactory(requireContext())).get(QuejasViewModel::class.java)

        quejaViewModel.quejas.observe(viewLifecycleOwner, { quejas ->
            quejaAdapter.actualizarQuejas(quejas)
        })

        root.findViewById<View>(R.id.btnNuevaQuejaView).setOnClickListener {
            findNavController().navigate(R.id.quejanuevaFragment)
        }

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener las quejas cuando se crea la vista del fragmento
        quejaViewModel.obtenerQuejas()
    }

}