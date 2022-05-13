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
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditProductoBinding

class editProductoFragment : Fragment() {

    lateinit var fBinding: FragmentEditProductoBinding
    private val args by navArgs<editProductoFragmentArgs>()
    private lateinit var viewModel: ProductoViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fBinding =
            FragmentEditProductoBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(ProductoViewModels::class.java)
        with(fBinding) {

            tvID.text = args.currentProduct.idProducto.toString()
            etNombre.setText(args.currentProduct.nombre)
            etDescripcion.setText(args.currentProduct.descripcion)
            etCantidad.setText(args.currentProduct.cantidad.toString())
            etPrecio.setText(args.currentProduct.precio.toString())
            etPeso.setText(args.currentProduct.peso.toString())

            btnEdit.setOnClickListener {
                updateProduct()
            }

            fBinding.btnVolver.setOnClickListener {
                findNavController().navigate(R.id.edit_products_to_products)
            }

            fBinding.deleteBudget.setOnClickListener{
                deleteProduct()
            }
        }
        setHasOptionsMenu(true)
        return fBinding.root
    }

    private fun updateProduct() {
        val nombre = fBinding.etNombre.text.toString()
        val descripcion = fBinding.etDescripcion.text.toString()
        val cantidad = fBinding.etCantidad.text.toString()
        val precio = fBinding.etPrecio.text.toString()
        val peso = fBinding.etPeso.text.toString()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && cantidad.isNotEmpty() &&
            precio.isNotEmpty() && peso.isNotEmpty()
        ) {
            val product = ProductoEntity(
                args.currentProduct.idProducto,
                nombre,
                descripcion,
                cantidad.toDouble(),
                precio.toDouble(),
                peso.toDouble(),
                true
            )

            viewModel.actualizarProducto(product)
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

    override fun onCreateOptionsMenu(
        menu: Menu, inflater:
        MenuInflater
    ) {
        inflater.inflate(R.menu.menuopcion, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar) {
            deleteProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarProducto(args.currentProduct)
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
        alerta.setTitle("Eliminando ${args.currentProduct.nombre}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentProduct.nombre}?")
        alerta.create().show()
    }
}

