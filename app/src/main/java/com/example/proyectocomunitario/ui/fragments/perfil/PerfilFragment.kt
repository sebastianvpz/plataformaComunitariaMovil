package com.example.proyectocomunitario.ui.fragments.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.databinding.FragmentPerfilBinding
import com.example.proyectocomunitario.model.Usuario


class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var viewModel: PerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)

        viewModel.usuario.observe(viewLifecycleOwner, { usuario ->
            usuario?.let {
                updateUI(usuario)
            }
        })

        viewModel.obtenerDatosUsuario(requireContext())

        // Botones
        binding.btnEventos.setOnClickListener {
            findNavController().navigate(R.id.perfilEventosFragment)
        }

        binding.btnCambiarPasswordView.setOnClickListener {
            findNavController().navigate(R.id.nuevapasswordFragment)
        }

        binding.btnModificarView.setOnClickListener {
            findNavController().navigate(R.id.perfilmodificarFragment)
        }
    }

    private fun updateUI(usuario: Usuario) {
        // Actualizar la UI con los datos del usuario
        binding.editNombresNo.setText(usuario.nombre)
        binding.editApellidosNo.setText(usuario.apellido)
        binding.ediEmailNo.setText(usuario.email)
        binding.editUsernameNo.setText(usuario.email)
        binding.editContraseANo.setText(".........")
    }
}