package com.rsamqui.bakingbills.fragments.editar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.viewmodels.PresupuestoViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditBudgetBinding
import kotlinx.android.synthetic.main.item_presupuesto.*
import kotlinx.android.synthetic.main.item_producto.*
import kotlinx.android.synthetic.main.item_producto.tvId

class EditBudgetFragment : Fragment() {

    lateinit var fBinding: FragmentEditBudgetBinding
    private val args by navArgs<EditBudgetFragmentArgs>()
    private lateinit var viewModel: PresupuestoViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fBinding =
            FragmentEditBudgetBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(PresupuestoViewModels::class.java)
        with(fBinding) {

            tvId.text = args.currentBudget.idPresupuesto.toString()
            etIngrediente.setText(args.currentBudget.ingrediente)
            etUnidades.setText(args.currentBudget.unidades.toString())
            etMedida.setText(args.currentBudget.medida)
            etPrecio.setText(args.currentBudget.precio.toString())
            etPrecioT.setText(args.currentBudget.total.toString())

            btnEdit.setOnClickListener {
                updateBudget()
            }

            fBinding.btnVolver.setOnClickListener {
                findNavController().navigate(R.id.edit_budget_to_budget)
            }

            fBinding.deleteBudget.setOnClickListener{
                deletePresupuesto()
            }

        }
        setHasOptionsMenu(true)
        return fBinding.root
    }

    private fun updateBudget() {
        val ingrediente = fBinding.etIngrediente.text.toString()
        val unidades = fBinding.etUnidades.text.toString()
        val medida = fBinding.etMedida.text.toString()
        val precio = fBinding.etPrecio.text.toString()
        val total = fBinding.etPrecioT.text.toString()

        if (ingrediente.isNotEmpty() && unidades.isNotEmpty() && medida.isNotEmpty() &&
            precio.isNotEmpty() && total.isNotEmpty()
        ) {
            val budget = PresupuestoEntity(
                args.currentBudget.idPresupuesto,
                ingrediente,
                unidades.toDouble(),
                medida,
                precio.toDouble(),
                total.toDouble(),
                true
            )

            viewModel.actualizarPresupuesto(budget)
            Toast.makeText(
                requireContext(), "Registro actualizado",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.edit_products_to_products)
        } else {
            Toast.makeText(requireContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG)
                .show()

        }

    }

    private fun deletePresupuesto(){

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarPresupuesto(args.currentBudget)
            Toast.makeText(
                requireContext(),
                "Registro eliminado satisfactoriamente...",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.edit_products_to_products)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando ${args.currentBudget.ingrediente}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentBudget.ingrediente}?")
        alerta.create().show()
    }
}