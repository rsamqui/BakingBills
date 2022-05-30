package com.rsamqui.bakingbills.fragments.lista

import UsuarioAdapter
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsamqui.bakingbills.api.ApiService
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.api.dataclass.Usuarios
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.bd.viewmodels.UsuarioViewModels
import com.rsamqui.bakingbills.databinding.FragmentUsuariosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UsuariosFragment : Fragment() {
    lateinit var fBinding: FragmentUsuariosBinding

    private lateinit var API: ApiService
    private lateinit var viewModel : UsuarioViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        fBinding = FragmentUsuariosBinding.inflate(layoutInflater)
        val adapter = UsuarioAdapter()
        val recycleView = fBinding.RcvListaUsuarios
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this).get(UsuarioViewModels::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        {
                usuario -> adapter.setData(usuario)
        })

        setHasOptionsMenu(true)
        API = Common.retrofitService
        return fBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternet()
        setupViews()
    }

    private fun setupViews() {
        with(fBinding) {
            addUser.setOnClickListener {
                it.findNavController().navigate(R.id.usuarios_to_add_usuarios)
            }
        }
    }

    private fun checkInternet(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            try {
                if(isConnected) {
                    searchAllUsuarios()
                } else{
                    Toast.makeText(requireContext(), "No hay conexión a Internet", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {

            }

        }
    }

    private fun searchAllUsuarios() {
        var list: ArrayList<Usuarios>
        CoroutineScope(Dispatchers.IO).launch {
            var count:  Int = 0
            val call = API.getAllUsuarios()

            list = call

            list.forEach{_ ->
                run {
                    for (i in 0..list.lastIndex) {
                        var id: Int = (list[i].idUsuario ?: Int) as Int
                        var username: String = list[i].username.toString()
                        var nombres: String = list[i].nombres.toString()
                        var apellidos: String = list[i].apellidos.toString()
                        var email: String = list[i].correo.toString()
                        var pwd: String = list[i].pwd.toString()

                        val usuario = UsuarioEntity(id, username, nombres, apellidos, email, pwd, true)
                        count++

                        viewModel.agregarUsuario(usuario)
                    }
                }
                if (count == list.size){
                    return@launch
                }
            }
        }
    }
}