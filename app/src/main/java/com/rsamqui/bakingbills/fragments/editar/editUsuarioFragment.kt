package com.rsamqui.bakingbills.fragments.editar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rsamqui.bakingbills.api.ApiService
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.bd.viewmodels.UsuarioViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class editUsuarioFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentEditUsuarioBinding
    private val args by navArgs<editUsuarioFragmentArgs>()
    private lateinit var viewModel: UsuarioViewModels

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        fBinding = FragmentEditUsuarioBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UsuarioViewModels::class.java)
        with(fBinding) {
            etUsuario.setText(args.currentUsuario.username)
            etNombres.setText(args.currentUsuario.nombres)
            etApellidos.setText(args.currentUsuario.apellidos)
            etEmail.setText(args.currentUsuario.correo)
            etPwd.setText(args.currentUsuario.pwd)

            btnActualizar.setOnClickListener {
                checkInternet()
            }
            fBinding.btnVolver.setOnClickListener {
                findNavController().navigate(R.id.edit_usuarios_to_usuarios)
            }
            fBinding.deleteUsuario.setOnClickListener{
                checkInternetDel()
            }
        }
        setHasOptionsMenu(true)
        API = Common.retrofitService
        return fBinding.root
    }

    private fun GuardarCambios() {
        val username = fBinding.etUsuario.text.toString()
        val nombres = fBinding.etNombres.text.toString()
        val apellidos = fBinding.etApellidos.text.toString()
        val correo = fBinding.etEmail.text.toString()
        val pwd = fBinding.etPwd.text.toString()
        val id = args.currentUsuario.idUsuario

        val jsonObject = JSONObject()
        jsonObject.put("idUsuario", id)
        jsonObject.put("username", username)
        jsonObject.put("nombres", nombres)
        jsonObject.put("apellidos", apellidos)
        jsonObject.put("correo", correo)
        jsonObject.put("pwd", pwd)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            if(username.isNotEmpty() && nombres.isNotEmpty() && apellidos.isNotEmpty()  && correo.isNotEmpty()   && pwd.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    API.editUsuario(requestBody)
                }
                var usuario = UsuarioEntity(id, username, nombres, apellidos, correo, pwd, true)
                viewModel.actualizarUsuario(usuario)
                Toast.makeText(
                    requireContext(), "Registro actualizado",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.edit_usuarios_to_usuarios)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Debe rellenar todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {

        }
    }

    override fun onCreateOptionsMenu(
        menu: Menu, inflater:
        MenuInflater
    ) {
        inflater.inflate(R.menu.menuopcion, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar) {
            delUsuario()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkInternet(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if(isConnected) {
                GuardarCambios()
            } else{
                Toast.makeText(requireContext(), "No hay conexión a Internet, no puede editar ni eliminar datos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkInternetDel(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if(isConnected) {
                delUsuario()
            } else{
                Toast.makeText(requireContext(), "No hay conexión a Internet, no puede editar ni eliminar datos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun ApiDelUsuario(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            API.delUsuario(id)
        }
    }

    private fun delUsuario() {
        val username = fBinding.etUsuario.text.toString()
        val nombres = fBinding.etNombres.text.toString()
        val apellidos = fBinding.etApellidos.text.toString()
        val correo = fBinding.etEmail.text.toString()
        val pwd = fBinding.etPwd.text.toString()

        val jsonObject = JSONObject()
        jsonObject.put("idUsuario", id)
        jsonObject.put("username", "$username")
        jsonObject.put("nombres", nombres)
        jsonObject.put("apellidos", apellidos)
        jsonObject.put("correo", correo)
        jsonObject.put("pwd", pwd)

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            var usuario = UsuarioEntity(args.currentUsuario.idUsuario, username, nombres, apellidos, correo, pwd, true)
            viewModel.eliminarUsuario(usuario)
            ApiDelUsuario(args.currentUsuario.idUsuario)
            Toast.makeText(
                requireContext(),
                "Registro eliminado",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.edit_usuarios_to_usuarios)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Eliminación cancelada",
                Toast.LENGTH_SHORT
            ).show()
        }
        alerta.setTitle("Eliminando ${args.currentUsuario.username}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentUsuario.username}?")
        alerta.create().show()
    }
}