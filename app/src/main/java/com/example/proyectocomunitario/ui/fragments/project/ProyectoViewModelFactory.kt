package com.example.proyectocomunitario.ui.fragments.project

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.service.ProyectoService

class ProyectoViewModelFactory(private val proyectoService: ProyectoService, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProyectoViewModel::class.java)) {
            return ProyectoViewModel(proyectoService, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
