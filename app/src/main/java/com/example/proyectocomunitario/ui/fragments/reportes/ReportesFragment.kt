package com.example.proyectocomunitario.ui.fragments.reportes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectocomunitario.R

class ReportesFragment : Fragment() {

    companion object {
        fun newInstance() = ReportesFragment()
    }

    private lateinit var viewModel: ReportesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reportes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}