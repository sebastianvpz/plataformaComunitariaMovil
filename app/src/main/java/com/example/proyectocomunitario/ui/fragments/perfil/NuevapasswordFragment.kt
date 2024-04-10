package com.example.proyectocomunitario.ui.fragments.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.R

class NuevapasswordFragment : Fragment() {

    private lateinit var viewModel: NuevapasswordViewModel
    private lateinit var editPasswordActual: EditText
    private lateinit var editNewPass: EditText
    private lateinit var editConfirmNewPassword: EditText
    private lateinit var btnModificarContraseña: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_nuevapassword, container, false)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(NuevapasswordViewModel::class.java)

        // Inicializar vistas
        editPasswordActual = root.findViewById(R.id.editPasswordActual)
        editNewPass = root.findViewById(R.id.editNewPass)
        editConfirmNewPassword = root.findViewById(R.id.editConfirmNewPassword)
        btnModificarContraseña = root.findViewById(R.id.btnModificarContraseña)

        // Configurar onClickListener para el botón de modificar contraseña
        btnModificarContraseña.setOnClickListener {
            val passwordActual = editPasswordActual.text.toString()
            val newPassword = editNewPass.text.toString()
            val confirmNewPassword = editConfirmNewPassword.text.toString()

            if (newPassword == confirmNewPassword) {
                viewModel.cambiarContraseña(requireContext(), passwordActual, newPassword)
            } else {
                Toast.makeText(requireContext(), "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar cambios en el éxito o error de la operación
        viewModel.exito.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show()
                // Aquí puedes realizar cualquier acción adicional después de cambiar la contraseña
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
}