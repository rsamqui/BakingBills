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
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {

    lateinit var fBinding: FragmentAddProductBinding
    private lateinit var viewModel: ProductoViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fBinding =
            FragmentAddProductBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(ProductoViewModels::class.java)

        fBinding.btnAgregar.setOnClickListener {
            guardarProducto()
        }

        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_products_to_products)
        }

        return fBinding.root
    }

    private fun guardarProducto() {
        val nombre = fBinding.etNombre.text.toString()
        val descripcion = fBinding.etDescripcion.text.toString()
        val cantidad = fBinding.etCantidad.text.toString()
        val precio = fBinding.etPrecio.text.toString()
        val peso = fBinding.etPeso.text.toString()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && cantidad.toString().isNotEmpty() &&
            precio.isNotEmpty() && peso.isNotEmpty()) {
            val producto = ProductoEntity(
                0, nombre, descripcion, cantidad.toDouble(), precio.toDouble(),
                peso.toDouble(), true
            )

            viewModel.agregarProducto(producto)
            Toast.makeText(
                requireContext(), "Registro Guardado",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.add_products_to_products)
        } else {
            Toast.makeText(
                requireContext(), "Debe rellenar todos los campos",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}



