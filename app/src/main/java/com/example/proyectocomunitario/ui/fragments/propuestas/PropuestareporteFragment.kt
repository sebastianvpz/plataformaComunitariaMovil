package com.example.proyectocomunitario.ui.fragments.propuestas

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectocomunitario.R

class PropuestareporteFragment : Fragment() {

    companion object {
        fun newInstance() = PropuestareporteFragment()
    }

    private val viewModel: PropuestareporteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_propuestareporte, container, false)
    }
}