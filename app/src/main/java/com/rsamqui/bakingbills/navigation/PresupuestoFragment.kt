package com.rsamqui.bakingbills.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.FragmentPresupuestoBinding

class PresupuestoFragment : Fragment() {

    private var _binding: FragmentPresupuestoBinding? = null
    private val binding: FragmentPresupuestoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPresupuestoBinding.inflate(
            inflater,
            container,
            false
        )

        binding.addBudget.setOnClickListener{
            findNavController().navigate(R.id.budget_to_add_budget)
        }

        return binding.root
    }


}