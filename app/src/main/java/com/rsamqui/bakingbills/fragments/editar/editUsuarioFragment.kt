package com.rsamqui.bakingbills.fragments.editar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.bd.viewmodels.UsuarioViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditUsuarioBinding

class editUsuarioFragment : Fragment() {
    lateinit var fBinding: FragmentEditUsuarioBinding
    private val args by navArgs<editUsuarioFragmentArgs>()
    private lateinit var viewModel: UsuarioViewModels
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding =
            FragmentEditUsuarioBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(this).get(UsuarioViewModels::class.java)
        with(fBinding) {

            etUsuario.setText(args.currentUsuario.username)
            etNombres.setText(args.currentUsuario.nombres)
            etApellidos.setText(args.currentUsuario.apellidos)
            etEmail.setText(args.currentUsuario.email)
            etPwd.setText(args.currentUsuario.pwd)
            btnActualizar.setOnClickListener {
                GuardarCambios()
            }

            fBinding.btnVolver.setOnClickListener{
                findNavController().navigate(R.id.edit_usuarios_to_usuarios)
            }
        }
        //Agregar menu
        setHasOptionsMenu(true)
        return fBinding.root

    }
    private fun GuardarCambios() {
        val username = fBinding.etUsuario.text.toString()
        val nombres = fBinding.etNombres.text.toString()
        val apellidos = fBinding.etApellidos.text.toString()
        val email = fBinding.etEmail.text.toString()
        val pwd = fBinding.etPwd.text.toString()

        if(username.isNotEmpty() && nombres.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty()   && pwd.isNotEmpty())
        {
            //Crear el objeto
            val user =
                UsuarioEntity(args.currentUsuario.idUsuario,
                    username, nombres, apellidos, email, pwd, true)
            //Actualizar
            viewModel.actualizarUsuario(user)
            Toast.makeText(requireContext(), "Registro actualizado",
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.edit_usuarios_to_usuarios)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show()
        }


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater:
    MenuInflater
    ) {
        inflater.inflate(R.menu.menuopcion, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.mnuEliminar) {
            eliminarUsuario()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun eliminarUsuario() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarUsuario(args.currentUsuario)
            Toast.makeText(
                requireContext(),
                "Registro eliminado satisfactoriamente...",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.edit_usuarios_to_usuarios)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando ${args.currentUsuario.username}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentUsuario.username}?")
        alerta.create().show()
    }
}
