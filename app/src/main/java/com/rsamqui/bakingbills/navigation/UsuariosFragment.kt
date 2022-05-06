package com.rsamqui.bakingbills.navigation

import UsuarioAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.viewmodels.UsuarioViewModels
import com.rsamqui.bakingbills.databinding.FragmentUsuariosBinding

class UsuariosFragment : Fragment() {
    lateinit var fBinding: FragmentUsuariosBinding
    private lateinit var viewModel : UsuarioViewModels
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentUsuariosBinding.inflate(layoutInflater)
        val adapter = UsuarioAdapter()
        val recycleView = fBinding.RcvListaUsuarios
        recycleView.adapter = adapter
        recycleView.layoutManager =
            LinearLayoutManager(requireContext())
        viewModel =
            ViewModelProvider(this).get(UsuarioViewModels::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        {user->
            adapter.setData(user)
        })

        return fBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }
    private fun setupViews() {
        with(fBinding) {
            addUser.setOnClickListener {

                it.findNavController().navigate(R.id.usuarios_to_add_usuarios)
            }
        }
    }
}