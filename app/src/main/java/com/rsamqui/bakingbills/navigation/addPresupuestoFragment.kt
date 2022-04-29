package com.rsamqui.bakingbills.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentAddPresupuestoBinding
import com.rsamqui.bakingbills.databinding.FragmentPresupuestoBinding

class addPresupuestoFragment : Fragment() {

    private var _binding: FragmentAddPresupuestoBinding? = null
    private val binding: FragmentAddPresupuestoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPresupuestoBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnVolver.setOnClickListener{
            findNavController().navigate(R.id.add_budget_to_budget)
        }

        return binding.root
    }


}