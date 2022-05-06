package com.rsamqui.bakingbills.fragments.lista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentIngredientesBinding
import com.rsamqui.bakingbills.databinding.FragmentLoginBinding

class IngredientesFragment : Fragment() {

    private var _binding: FragmentIngredientesBinding? = null
    private val binding : FragmentIngredientesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientesBinding.inflate(
            inflater,
            container,
            false
        )

        binding.addIngredients.setOnClickListener {
            findNavController().navigate(R.id.ingredientes_to_add_ingredientes)
        }

        return binding.root
    }


}