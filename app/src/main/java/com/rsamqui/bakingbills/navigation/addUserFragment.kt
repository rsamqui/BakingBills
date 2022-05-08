package com.rsamqui.bakingbills.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentAddUserBinding

class addUserFragment : Fragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val binding : FragmentAddUserBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnVolver.setOnClickListener{
            findNavController().navigate(R.id.add_user_to_usuarios)
        }

        return binding.root
    }
}