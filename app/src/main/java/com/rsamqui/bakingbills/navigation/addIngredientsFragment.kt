package com.rsamqui.bakingbills.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.viewmodels.IngredienteViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddIngredientesBinding
import com.rsamqui.bakingbills.R

class addIngredientsFragment : Fragment() {
    lateinit var fBinding: FragmentAddIngredientesBinding
    private lateinit var viewModel: IngredienteViewModels

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fBinding = FragmentAddIngredientesBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(IngredienteViewModels::class.java)
        fBinding.btnAgregar.setOnClickListener {
            guardarRegistro()
        }
        return fBinding.root
    }

    private fun guardarRegistro() {
        val name = fBinding.etNombre.text.toString()
        val quantity = fBinding.etCantidad.text.toString().toInt()
        val price = fBinding.etPrecio.text.toString().toDouble()
        val ingrediente = IngredienteEntity(0, name, quantity, price)
        viewModel.agregarIngrediente(ingrediente)
        Toast.makeText(requireContext(), "Registro guardado", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.add_ingredientes_to_ingredientes)
    }
}