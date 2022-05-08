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
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.viewmodels.IngredienteViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditIngredientesBinding

class editIngredientesFragment : Fragment() {

    lateinit var fBinding: FragmentEditIngredientesBinding
    private val args by navArgs<editIngredientesFragmentArgs>()
    private lateinit var viewModel: IngredienteViewModels

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        fBinding = FragmentEditIngredientesBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(IngredienteViewModels::class.java)
        with(fBinding) { etIngrediente.setText(args.currentIngrediente.nombre)
            etCantidad.setText(args.currentIngrediente.cantidad.toString())
            etPrecio.setText(args.currentIngrediente.precio.toString())
            btnEditar.setOnClickListener {
                GuardarCambios()
            }
        }
        setHasOptionsMenu(true)
        return fBinding.root
    }

    private fun GuardarCambios() {
        val name = fBinding.etIngrediente.text.toString()
        val quantity = fBinding.etCantidad.text.toString()
        val price = fBinding.etPrecio.text.toString()

        if(name.isNotEmpty() && quantity.isNotEmpty() && price.isNotEmpty())
        {
            val ingrediente = IngredienteEntity(args.currentIngrediente.idIngrediente,
                name, quantity.toDouble(), price.toDouble(),)

            viewModel.actualizarIngrediente(ingrediente)
            Toast.makeText(requireContext(), "Registro actualizado",
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.edit_ingredientes_to_ingredientes)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menuopcion, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar) {
            eliminarIngrediente()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun eliminarIngrediente() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ -> viewModel.eliminarIngrediente(args.currentIngrediente)
            Toast.makeText( requireContext(), "Ingrediente eliminado satisfactoriamente...", Toast.LENGTH_LONG ).show()
            findNavController().navigate(R.id.edit_ingredientes_to_ingredientes)
        }
        alerta.setNegativeButton("No") { _, _ -> Toast.makeText( requireContext(), "Operación cancelada...", Toast.LENGTH_LONG ).show()
        }
        alerta.setTitle("Eliminando ${args.currentIngrediente.nombre}")
        alerta.setMessage("¿Esta seguro de eliminar ${args.currentIngrediente.nombre}?")
        alerta.create().show()
    }
}