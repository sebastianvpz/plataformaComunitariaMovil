package com.example.proyectocomunitario.ui.fragments.propuestas

import android.app.Activity
import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.databinding.FragmentProuestanuevaBinding
import com.example.proyectocomunitario.model.PropuestaRequest
import java.io.ByteArrayOutputStream


class ProuestanuevaFragment : Fragment() {

    private lateinit var binding: FragmentProuestanuevaBinding
    private lateinit var viewModel: ProuestanuevaViewModel
    private var encodedImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProuestanuevaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProuestanuevaViewModel::class.java)

        binding.btnNewPropuesta.setOnClickListener {
            guardarPropuesta()
        }

        binding.imagePickerPropuesta.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            val bitmap: Bitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(selectedImage!!))
            binding.imagePickerPropuesta.setImageBitmap(bitmap)
            encodedImage = encodeImage(bitmap)
        }
    }

    private fun encodeImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun guardarPropuesta() {
        val titulo = binding.editTitleNewPropuesta.text.toString().trim()
        val descripcion = binding.editDescNewPropuesta.text.toString().trim()
        val ubicacion = binding.editUbicacionNewPropuesta.text.toString().trim()

        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("user_id", "")



        if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (encodedImage.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Por favor, seleccione una imagen", Toast.LENGTH_SHORT).show()
            return
        }
        if (!usuarioId.isNullOrEmpty()){
            val propuesta = PropuestaRequest(encodedImage!!, titulo, descripcion, ubicacion, usuarioId.toLong())
            viewModel.guardarPropuesta(requireContext(), propuesta)

            viewModel.mensaje.observe(viewLifecycleOwner) { mensaje ->
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            }

            viewModel.error.observe(viewLifecycleOwner) { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

        }

    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 100
    }
}