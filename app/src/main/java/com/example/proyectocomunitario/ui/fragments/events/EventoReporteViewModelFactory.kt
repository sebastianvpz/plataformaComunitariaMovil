package com.example.proyectocomunitario.ui.fragments.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.service.EventoService


class EventoReporteViewModelFactory(private val reporteEventoService: EventoService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EnventoreporteViewModel::class.java)) {
            return EnventoreporteViewModel(reporteEventoService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}