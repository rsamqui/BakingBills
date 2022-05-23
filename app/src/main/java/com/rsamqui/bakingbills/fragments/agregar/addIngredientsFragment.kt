package com.rsamqui.bakingbills.fragments.agregar

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
        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_ingredientes_to_ingredientes)
        }
        return fBinding.root
    }

    private fun guardarRegistro() {        
        val name = fBinding.etNombre.text.toString()
        val quantity = fBinding.etMedida.text.toString()
        val price = fBinding.etPrecio.text.toString()
        
        if(name.isNotEmpty() && quantity.isNotEmpty() && price.isNotEmpty()){
            val ingrediente = IngredienteEntity(0, name, quantity.toDouble(), price.toDouble())
            viewModel.agregarIngrediente(ingrediente)
            Toast.makeText(requireContext(), "Registro guardado", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.add_ingredientes_to_ingredientes)
        }else{
            Toast.makeText(requireContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show()
        }
    }
}