package com.rsamqui.bakingbills.fragments.lista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.adapters.ProductoAdapter
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentProductosBinding

class ProductosFragment : Fragment() {

    lateinit var fBinding: FragmentProductosBinding
    private lateinit var viewModel: ProductoViewModels
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentProductosBinding.inflate(layoutInflater)
        val adapter = ProductoAdapter()
        val recycleView = fBinding.rcvListaProductos
        recycleView.adapter = adapter
        recycleView.layoutManager =
            LinearLayoutManager(requireContext())
        viewModel =
            ViewModelProvider(this).get(ProductoViewModels::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        { producto ->
            adapter.setData(producto)
        })

        return fBinding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState:
        Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(fBinding) {
            addProduct.setOnClickListener {

                it.findNavController().navigate(R.id.products_to_add_products)
            }
        }
    }
}