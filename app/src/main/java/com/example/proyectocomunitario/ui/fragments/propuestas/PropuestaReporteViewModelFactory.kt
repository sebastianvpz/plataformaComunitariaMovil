package com.example.proyectocomunitario.ui.fragments.propuestas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.service.PropuestaService

class PropuestaReporteViewModelFactory(private val propuestaService: PropuestaService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropuestaReporteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropuestaReporteViewModel(propuestaService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

