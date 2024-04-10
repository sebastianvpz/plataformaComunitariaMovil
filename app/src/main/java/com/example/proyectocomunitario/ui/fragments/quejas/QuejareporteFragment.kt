package com.example.proyectocomunitario.ui.fragments.quejas

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectocomunitario.R

class QuejareporteFragment : Fragment() {

    companion object {
        fun newInstance() = QuejareporteFragment()
    }

    private val viewModel: QuejareporteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_quejareporte, container, false)
    }
}