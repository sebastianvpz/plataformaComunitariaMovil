package com.example.proyectocomunitario.ui.fragments.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.proyectocomunitario.databinding.FragmentPerfilmodificarBinding
import com.example.proyectocomunitario.model.Usuario

class PerfilmodificarFragment : Fragment() {

    private var _binding: FragmentPerfilmodificarBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PerfilmodificarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilmodificarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PerfilmodificarViewModel::class.java)

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Manejar errores, como mostrar un mensaje al usuario
        }

        viewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            usuario?.let { mostrarDatosUsuario(it) }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnModificar.setOnClickListener {
            modificarUsuario()
        }

        viewModel.obtenerDatosUsuario(requireContext())
    }

    private fun mostrarDatosUsuario(usuario: Usuario) {
        binding.editNombres.setText(usuario.nombre)
        binding.editApellidos.setText(usuario.apellido)
        binding.editEmail.setText(usuario.email)
        binding.editUnsername.setText(usuario.username)
    }

    private fun modificarUsuario() {
        val nuevoUsuario = Usuario(
            nombre = binding.editNombres.text.toString(),
            apellido = binding.editApellidos.text.toString(),
            email = binding.editEmail.text.toString(),
            username = binding.editUnsername.text.toString()
        )

        viewModel.modificarUsuario(requireContext(), nuevoUsuario)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}