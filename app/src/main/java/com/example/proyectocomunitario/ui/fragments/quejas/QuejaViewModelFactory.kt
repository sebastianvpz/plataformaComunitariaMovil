package com.example.proyectocomunitario.ui.fragments.quejas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import com.example.proyectocomunitario.service.QuejaService

class QuejaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuejasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuejasViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
