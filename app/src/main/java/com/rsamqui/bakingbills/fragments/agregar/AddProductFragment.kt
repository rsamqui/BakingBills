package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.databinding.FragmentAddProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class addProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding: FragmentAddProductBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnVolver.setOnClickListener{
            findNavController().navigate(R.id.add_products_to_products)
        }
        return binding.root
    }
}

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding: FragmentAddProductBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_products_to_products)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)
        val db: BDPanaderia = BDPanaderia.getInstance(this.requireContext().applicationContext)
        val dao: ProductoDao = db.productoDao()

        with(binding) {
            btnAgregar.setOnClickListener {

                val id = ProductoEntity(
                    0,
                    etNombre.text.toString(),
                    etDescripcion.text.toString(),
                    etCantidad.text.toString().toDouble(),
                    etPrecio.text.toString().toDouble(),
                    etPeso.text.toString().toDouble(),
                    false
                )

                CoroutineScope(Dispatchers.Main).launch{
                    dao.insertProduct(id)
                }

                navController.navigate(R.id.add_products_to_products)
            }
        }
    }

}