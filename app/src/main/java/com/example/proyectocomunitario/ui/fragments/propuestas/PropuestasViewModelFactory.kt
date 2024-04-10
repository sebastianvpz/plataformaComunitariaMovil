package com.example.proyectocomunitario.ui.fragments.propuestas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.service.PropuestaService

class PropuestasViewModelFactory(private val propuestaService: PropuestaService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropuestasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropuestasViewModel(propuestaService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
