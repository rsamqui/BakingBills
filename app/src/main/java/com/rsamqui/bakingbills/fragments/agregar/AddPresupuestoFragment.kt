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
import kotlinx.android.synthetic.main.fragment_add_presupuesto.*

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

        calcularTotal()

        return fBinding.root
    }

    private fun guardarPresupuesto() {
        val ingrediente = fBinding.etIngrediente.text.toString()
        val unidades = fBinding.etUnits.text.toString()
        val medida = fBinding.etMedida.text.toString()
        val precio = fBinding.etPrice.text.toString()
        val total = fBinding.etPrecioT.text.toString()

        if (ingrediente.isNotEmpty() && unidades.isNotEmpty() && medida.isNotEmpty() &&
            precio.isNotEmpty() && total.isNotEmpty()
        ) {
            val presupuesto = PresupuestoEntity(
                0, ingrediente, unidades.toDouble(), medida, precio.toDouble(),
                total.toDouble(), true
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

    private fun calcularTotal() {
        fBinding.btnCalcular.setOnClickListener {
            val cant: Double = (etUnits.text.toString()).toDouble()
            val price: Double = (etPrice.text.toString()).toDouble()

            val total = (cant * price).toString()

            etPrecioT.setText(total)
        }
    }
}


