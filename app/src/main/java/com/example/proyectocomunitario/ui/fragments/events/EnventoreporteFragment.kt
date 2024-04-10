import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectocomunitario.databinding.FragmentEnventoreporteBinding
import com.example.proyectocomunitario.network.RetrofitClient
import com.example.proyectocomunitario.ui.fragments.events.EnventoreporteViewModel
import com.example.proyectocomunitario.ui.fragments.events.EventoReporteViewModelFactory
import com.google.android.material.snackbar.Snackbar

class EnventoreporteFragment : Fragment() {

    private lateinit var binding: FragmentEnventoreporteBinding
    private lateinit var viewModel: EnventoreporteViewModel
    private var eventoId: Long = -1 // EventoId recibido como argumento

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnventoreporteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el argumento del Bundle
        eventoId = arguments?.getLong("eventoId") ?: -1

        val reporteEventoService = RetrofitClient.eventoService
        val viewModelFactory = EventoReporteViewModelFactory(reporteEventoService)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EnventoreporteViewModel::class.java)

        binding.btnReportar.setOnClickListener {
            val mensaje = binding.editDescripcionReporte.text.toString()
            if (mensaje.isNotEmpty() && eventoId != -1L) {
                viewModel.reportarEvento(requireContext(), eventoId, mensaje)
                Snackbar.make(requireView(), "Evento reportado con éxito", Snackbar.LENGTH_SHORT).show()
            } else {
                // Manejar mensaje o eventoId inválido si es necesario
            }
        }

        // Observar el LiveData para el mensaje de éxito
        viewModel.reporteExitoso.observe(viewLifecycleOwner, Observer { reporteExitoso ->
            if (reporteExitoso) {
                // Muestra un Toast con el mensaje de éxito
                Toast.makeText(requireContext(), "Evento reportado con éxito", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
