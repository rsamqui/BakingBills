package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.api.ApiService
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.dao.UsuarioDao
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.bd.viewmodels.UsuarioViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception

class addUserFragment : Fragment() {
    private lateinit var API: ApiService
    lateinit var fBinding: FragmentAddUsuarioBinding
    private lateinit var viewModel: UsuarioViewModels
    private var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fBinding = FragmentAddUsuarioBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UsuarioViewModels::class.java)
        checkInternet()
        fBinding.btnAgregar.setOnClickListener {
            guardarRegistroOffline()
        }
        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_user_to_usuarios)
        }
        API = Common.retrofitService
        count = 0
        return fBinding.root
    }



    private fun guardarRegistro() {
        val username = fBinding.etUsuario.text.toString()
        val nombres = fBinding.etNombres.text.toString()
        val apellidos = fBinding.etApellidos.text.toString()
        val correo = fBinding.etEmail.text.toString()
        val pwd = fBinding.etPwd.text.toString()

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
                    if(count == 0) {
                        API.addUsuario(requestBody)
                    }

                    var usuario = UsuarioEntity(id, username, nombres, apellidos, correo, pwd, true)

                    viewModel.agregarUsuario(usuario)
                }
                Toast.makeText(
                    requireContext(), "Registro guardado",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.add_user_to_usuarios)
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

    fun checkInternet(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val BD: BDPanaderia = BDPanaderia.getInstance(requireContext().applicationContext)
                val daoI: UsuarioDao = BD.usuarioDao()

                CoroutineScope(Dispatchers.IO).launch {
                    var usuarios: List<UsuarioEntity> = daoI.getAll()
                    var usuarioApi = API.getAllUsuarios()
                    var usuariosSize = usuarios.size
                    var usuariosApiSize = usuarioApi.size

                    if (usuariosSize > usuariosApiSize) {
                        for (i in 0..usuarios.lastIndex) {
                            var id: Int = usuarios[i].idUsuario
                            var username: String = usuarios[i].username
                            var nombres: String = usuarios[i].nombres
                            var apellidos: String = usuarios[i].apellidos
                            var correo: String = usuarios[i].correo
                            var pwd: String = usuarios[i].pwd

                            val jsonObject = JSONObject()
                            jsonObject.put("idUsuario", id)
                            jsonObject.put("username", username)
                            jsonObject.put("nombres", nombres)
                            jsonObject.put("apellidos", apellidos)
                            jsonObject.put("correo", correo)
                            jsonObject.put("pwd", pwd)

                            val jsonObjectString = jsonObject.toString()
                            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                            var usuario = UsuarioEntity(id, username, nombres, apellidos, correo, pwd, true)
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.eliminarUsuario(usuario)

                                API.addUsuario(requestBody)
                            }
                        }
                    }

                }
            }
            else {
                Toast.makeText(requireContext(),"No hay conexión a Internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarRegistroOffline() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            try {
                if(isConnected) {
                    guardarRegistro()
                    count = 4
                } else {
                    val username = fBinding.etUsuario.text.toString()
                    val nombres = fBinding.etNombres.text.toString()
                    val apellidos = fBinding.etApellidos.text.toString()
                    val correo = fBinding.etEmail.text.toString()
                    val pwd = fBinding.etPwd.text.toString()

                    var usuario = UsuarioEntity(id, username, nombres, apellidos, correo, pwd, true)
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.agregarUsuario(usuario)
                    }
                    Toast.makeText(requireContext(), "Guardado localmente", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.add_user_to_usuarios)

                    Toast.makeText(
                        requireContext(),"No hay conexión a Internet", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

            }
        }
    }
}