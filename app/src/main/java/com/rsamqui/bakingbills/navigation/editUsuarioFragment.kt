package com.rsamqui.bakingbills.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentEditUsuarioBinding

class editUsuarioFragment : Fragment() {

    private var _binding: FragmentEditUsuarioBinding? = null
    private val binding: FragmentEditUsuarioBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditUsuarioBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnVolver.setOnClickListener{
            findNavController().navigate(R.id.edit_usuarios_to_usuarios)
        }

        return binding.root
    }


}