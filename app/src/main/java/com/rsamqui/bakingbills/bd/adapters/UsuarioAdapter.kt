import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.databinding.ItemUsuarioBinding
import com.rsamqui.bakingbills.fragments.lista.UsuariosFragmentDirections

class UsuarioAdapter :
    RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder>() {
    private var listadoUsuario = emptyList<UsuarioEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): UsuarioHolder {
        val binding =

            ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return UsuarioHolder(binding)
    }
    override fun onBindViewHolder(holder: UsuarioHolder,
                                  position: Int) {
        holder.bind(
            listadoUsuario[position]
        )
    }
    override fun getItemCount(): Int = listadoUsuario.size
    fun setData(users: List<UsuarioEntity>) {
        this.listadoUsuario = users
        notifyDataSetChanged()
    }

    inner class UsuarioHolder(val binding: ItemUsuarioBinding)
        :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(usuario: UsuarioEntity) {
            with(binding) {
                TvIdUsuario.text = usuario.idUsuario.toString()
                TvUsername.text = usuario.username
                TvNombres.text = usuario.nombres
                TvApellidos.text = usuario.apellidos
                ClFilaUsuario.setOnClickListener {
                    val action =
                        UsuariosFragmentDirections.usuarioToEditUsuario(usuario)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}