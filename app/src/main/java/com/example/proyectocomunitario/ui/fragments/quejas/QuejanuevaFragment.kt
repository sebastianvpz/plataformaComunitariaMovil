package com.example.proyectocomunitario.ui.fragments.quejas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.databinding.FragmentQuejanuevaBinding
import com.example.proyectocomunitario.model.QuejaRequest
import com.example.proyectocomunitario.model.SharedPreferencesManager
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date


private const val DATE_FORMAT = "dd-MM-yy"

class QuejanuevaFragment : Fragment() {

    private lateinit var binding: FragmentQuejanuevaBinding
    private lateinit var quejaViewModel: QuejanuevaViewModel
    private var encodedImage: String? = null
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuejanuevaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quejaViewModel = ViewModelProvider(this).get(QuejanuevaViewModel::class.java)
        sharedPreferencesManager = SharedPreferencesManager


        binding.imagePicker.setOnClickListener {
            openGallery()
        }

        binding.btnPublicarQueja.setOnClickListener {
            guardarQueja()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }



    private fun guardarQueja() {
        val titulo = binding.editQuejaTitulo.text.toString().trim()
        val descripcion = binding.editNewQuejaDescripcion.text.toString().trim()
        val ubicacion = binding.editNewQuejaUbicacion.text.toString().trim()
        val fechaReporte = formatDate(System.currentTimeMillis())
        val estado = "Pendiente" // Puedes ajustar esto según tus necesidades
        val usuarioId = sharedPreferencesManager.getUserId(requireContext());
        Log.d("QuejaNuevaFragment","titulo: $titulo")
        Log.d("QuejaNuevaFragment","descripcion: $descripcion")
        Log.d("QuejaNuevaFragment","ubicacion: $ubicacion")
        Log.d("QuejaNuevaFragment","fechaReporte: $fechaReporte")
        Log.d("QuejaNuevaFragment","estado: $estado")
        Log.d("QuejaNuevaFragment","usuarioId: $usuarioId")


        if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (encodedImage.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Por favor, seleccione una imagen", Toast.LENGTH_SHORT).show()
            return
        }


        if (!usuarioId.isNullOrEmpty()){
            val quejaRequest = QuejaRequest(encodedImage!!, titulo, descripcion, ubicacion, fechaReporte, estado, usuarioId.toLong() )
            quejaViewModel.guardarQueja(requireContext(), quejaRequest)
            quejaViewModel.error.observe(viewLifecycleOwner, { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            })
            quejaViewModel.mensaje.observe(viewLifecycleOwner, { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                // Aquí puedes agregar lógica adicional según tus necesidades, como navegar a otro fragmento, por ejemplo
            })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            val bitmap: Bitmap = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(selectedImage!!))
            binding.imagePicker.setImageBitmap(bitmap)
            encodedImage = encodeImage(bitmap)
        }
    }

    private fun encodeImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        return dateFormat.format(Date(timestamp))
    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 100
    }
}