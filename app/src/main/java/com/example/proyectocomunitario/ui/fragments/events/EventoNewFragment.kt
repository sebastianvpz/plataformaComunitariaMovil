package com.example.proyectocomunitario.ui.fragments.events

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.databinding.FragmentEventoNewBinding
import com.example.proyectocomunitario.model.EventoRequest
import java.io.ByteArrayOutputStream

class EventoNewFragment : Fragment() {

    private lateinit var binding: FragmentEventoNewBinding
    private lateinit var viewModel: EventoNewViewModel
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventoNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventoNewViewModel::class.java)

        binding.imagePickerEvento.setOnClickListener {
            openGallery()
        }

        binding.btnNewEvento.setOnClickListener {
            guardarEvento()
        }
        viewModel.mensaje.observe(viewLifecycleOwner, Observer { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
        })
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICKER)
    }

    private fun guardarEvento() {
        val titulo = binding.editTitleNewEvento.text.toString().trim()
        val descripcion = binding.editDescNewEvento.text.toString().trim()
        val fechaHora = binding.editFechaNewEvento.text.toString().trim()
        val ubicacion = binding.editUbicacionNewEvento.text.toString().trim()

        if (titulo.isEmpty() || descripcion.isEmpty() || fechaHora.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        selectedImageUri?.let { uri ->
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val imgBase64 = encodeImageToBase64(bitmap)

            val eventoRequest = EventoRequest(
                imgBase64,
                titulo,
                descripcion,
                fechaHora,
                ubicacion
            )

            viewModel.guardarEvento(requireContext(), eventoRequest)
        } ?: run {
            Toast.makeText(requireContext(), "Por favor seleccione una imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_PICKER) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.imagePickerEvento.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICKER = 100
    }
}
