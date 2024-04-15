package com.example.proyectocomunitario.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectocomunitario.model.Noticia
import com.example.proyectocomunitario.service.NoticiaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val noticiaService: NoticiaService) : ViewModel() {

    private val _noticias = MutableLiveData<List<Noticia>>()
    val noticias: LiveData<List<Noticia>> = _noticias

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarNoticias() {
        noticiaService.obtenerNoticias().enqueue(object : Callback<List<Noticia>> {
            override fun onResponse(call: Call<List<Noticia>>, response: Response<List<Noticia>>) {
                if (response.isSuccessful) {
                    _noticias.value = response.body()
                } else {
                    _error.value = "Error al cargar las noticias: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<Noticia>>, t: Throwable) {
                _error.value = "Error de red al cargar las noticias: ${t.message}"
            }
        })
    }
}
