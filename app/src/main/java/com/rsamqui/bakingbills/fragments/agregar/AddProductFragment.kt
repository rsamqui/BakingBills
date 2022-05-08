package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.dao.ProductoDao
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        fBinding.btnAgregar.setOnClickListener{
            guardarProducto()
        }

        return fBinding.root
        }

    private fun guardarProducto() {
        val nombre = fBinding.etIngrediente.text.toString()
        val descripcion = fBinding.etUnidades.text.toString()
        val cantidad = fBinding.etCantidad.text.toString().toDouble()
        val precio = fBinding.etPrecio.text.toString().toDouble()
        val peso = fBinding.etPeso.text.toString().toDouble()

        if(nombre.isNotEmpty() && descripcion.isNotEmpty()){
            val producto = ProductoEntity(0,nombre, descripcion, cantidad, precio,
            peso, true)

            viewModel.agregarProducto(producto)
            Toast.makeText(requireContext(), "Registro Guardado",
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.add_products_to_products)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe rellenar todos los campos",
                Toast.LENGTH_LONG).show()
        }
    }
}



