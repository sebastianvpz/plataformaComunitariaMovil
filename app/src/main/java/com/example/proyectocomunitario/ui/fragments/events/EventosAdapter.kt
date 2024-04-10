import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocomunitario.R
import com.example.proyectocomunitario.model.Evento

class EventosAdapter(
    private val onItemClick: (Evento) -> Unit,
    private val onAsistirButtonClick: (Evento) -> Unit, // Listener para el botón "Asistir"
    private val onReportButtonClick: (Long) -> Unit // Listener para el botón "Reportar"
) : ListAdapter<Evento, EventosAdapter.EventoViewHolder>(EventoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_eventos, parent, false)
        return EventoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val currentEvento = getItem(position)
        holder.bind(currentEvento)
        holder.itemView.setOnClickListener { onItemClick(currentEvento) }
        holder.asistirButton.setOnClickListener { onAsistirButtonClick(currentEvento) } // Agregar listener al botón "Asistir"
        holder.reportButton.setOnClickListener { onReportButtonClick(currentEvento.id) } // Agregar listener al botón "Reportar"
    }

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.eventosTitle)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.eventosDescription)
        private val fechaTextView: TextView = itemView.findViewById(R.id.eventosDate)
        val asistirButton: Button = itemView.findViewById(R.id.btnAsistir) // Botón "Asistir"
        private val imagenEvento: ImageView = itemView.findViewById(R.id.eventosImage)
        val reportButton: Button = itemView.findViewById(R.id.btnReportar) // Botón "Reportar"

        fun bind(evento: Evento) {
            tituloTextView.text = evento.titulo
            descripcionTextView.text = evento.descripcion
            fechaTextView.text = evento.fechaHora

            // Decode base64 string to byte array
            val decodedImageBytes = Base64.decode(evento.img, Base64.DEFAULT)

            // Convert byte array to bitmap and set it to ImageView
            val decodedImage = decodedImageBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            imagenEvento.setImageBitmap(decodedImage)

            // Set click listener for asistirButton if needed
        }
    }

    private class EventoDiffCallback : DiffUtil.ItemCallback<Evento>() {
        override fun areItemsTheSame(oldItem: Evento, newItem: Evento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Evento, newItem: Evento): Boolean {
            return oldItem == newItem
        }
    }
}
