package com.example.proyectocomunitario.ui.fragments.events

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.service.EventoService

class EventosViewModelFactory(private val eventoService: EventoService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventosViewModel::class.java)) {
            return EventosViewModel(eventoService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}