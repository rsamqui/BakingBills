package com.rsamqui.bakingbills.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentUsuariosBinding

class UsuariosFragment : Fragment() {

    private var _binding: FragmentUsuariosBinding? = null
    private val binding : FragmentUsuariosBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsuariosBinding.inflate(
            inflater,
            container,
            false
        )

        binding.addUser.setOnClickListener{
            findNavController().navigate(R.id.usuarios_to_add_usuarios)
        }

        return binding.root
    }
}