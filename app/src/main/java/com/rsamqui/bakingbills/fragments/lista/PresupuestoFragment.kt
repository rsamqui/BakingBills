package com.rsamqui.bakingbills.fragments.lista

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.adapters.PresupuestoAdapter
import com.rsamqui.bakingbills.bd.viewmodels.PresupuestoViewModels
import com.rsamqui.bakingbills.databinding.FragmentPresupuestoBinding
import com.rsamqui.bakingbills.databinding.ItemPresupuestoBinding
import com.rsamqui.bakingbills.fragments.editar.EditBudgetFragmentArgs

class PresupuestoFragment : Fragment() {

    lateinit var fBinding: FragmentPresupuestoBinding
    private lateinit var viewModel: PresupuestoViewModels
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentPresupuestoBinding.inflate(layoutInflater)
        val adapter = PresupuestoAdapter()
        val recycleView = fBinding.rcvListaPresupuesto
        recycleView.adapter = adapter
        recycleView.layoutManager =
            LinearLayoutManager(requireContext())
        viewModel =
            ViewModelProvider(this).get(PresupuestoViewModels::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        { presupuesto ->
            adapter.setData(presupuesto)
        })

        return fBinding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState:
        Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(fBinding) {
            addBudget.setOnClickListener {

                it.findNavController().navigate(R.id.budget_to_add_budget)
            }
        }
    }
}