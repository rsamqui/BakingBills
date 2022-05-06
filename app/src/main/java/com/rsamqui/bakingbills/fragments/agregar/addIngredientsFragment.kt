package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentAddIngredientesBinding
import com.rsamqui.bakingbills.databinding.FragmentIngredientesBinding
import com.rsamqui.bakingbills.databinding.FragmentLoginBinding

class addIngredientsFragment : Fragment() {

    private var _binding: FragmentAddIngredientesBinding? = null
    private val binding : FragmentAddIngredientesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddIngredientesBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnVolver.setOnClickListener{
            findNavController().navigate(R.id.add_ingredientes_to_ingredientes)
        }

        return binding.root
    }
}