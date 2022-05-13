package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.viewmodels.PresupuestoViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddPresupuestoBinding

class AddPresupuestoFragment : Fragment() {

    lateinit var fBinding: FragmentAddPresupuestoBinding
    private lateinit var viewModel: PresupuestoViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fBinding =
            FragmentAddPresupuestoBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(PresupuestoViewModels::class.java)

        fBinding.btnAgregar.setOnClickListener {
            guardarPresupuesto()
        }

        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_budget_to_budget)
        }

        return fBinding.root
    }

    private fun guardarPresupuesto() {
        val ingrediente = fBinding.etIngrediente.text.toString()
        val unidades = fBinding.etUnidades.text.toString().toDouble()
        val medida = fBinding.etMedida.text.toString()
        val precio = fBinding.etPrecio.text.toString().toDouble()
        val total = fBinding.etPrecioT.text.toString().toDouble()

        if (ingrediente.isNotEmpty() && unidades.toString().isNotEmpty() && medida.isNotEmpty() &&
            precio.toString().isNotEmpty() && total.toString().isNotEmpty()) {
            val presupuesto = PresupuestoEntity(
                0, ingrediente, unidades, medida, precio,
                total, true
            )

            viewModel.agregarPresupuesto(presupuesto)
            Toast.makeText(
                requireContext(), "Registro Guardado",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.add_budget_to_budget)
        } else {
            Toast.makeText(
                requireContext(), "Debe rellenar todos los campos",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}