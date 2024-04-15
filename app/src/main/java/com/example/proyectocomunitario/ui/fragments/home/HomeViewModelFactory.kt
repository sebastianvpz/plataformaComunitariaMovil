package com.example.proyectocomunitario.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.service.NoticiaService

class HomeViewModelFactory(private val noticiaService: NoticiaService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(noticiaService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
