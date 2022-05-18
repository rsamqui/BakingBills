package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.bd.viewmodels.UsuarioViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddUsuarioBinding

class addUserFragment : Fragment() {
    lateinit var fBinding: FragmentAddUsuarioBinding
    private lateinit var viewModel: UsuarioViewModels
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        fBinding =
            FragmentAddUsuarioBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(this).get(UsuarioViewModels::class.java)
        
        fBinding.btnVolver.setOnClickListener{
            findNavController().navigate(R.id.add_user_to_usuarios)
        }
        fBinding.btnAgregar.setOnClickListener {
            guardarRegistro()
        }
        return fBinding.root


    }
    private fun guardarRegistro() {
        //val baseDatos = MainBaseDatos.getDataBase(this)
        val username = fBinding.etUsuario.text.toString()
        val nombres = fBinding.etNombres.text.toString()
        val apellidos = fBinding.etApellidos.text.toString()
        val email = fBinding.etEmail.text.toString()
        val pwd = fBinding.etPwd.text.toString()


        if(username.isNotEmpty() && nombres.isNotEmpty() && apellidos.isNotEmpty()  && email.isNotEmpty()   && pwd.isNotEmpty())
        {
            //Crear objeto
            val usuario = UsuarioEntity(0, username,
                nombres, apellidos, email, pwd, true)
            //Agregar nuevo usuario
            viewModel.agregarUsuario(usuario)
            Toast.makeText(requireContext(), "Registro guardado",
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.add_user_to_usuarios)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show()
        }



    }
}
