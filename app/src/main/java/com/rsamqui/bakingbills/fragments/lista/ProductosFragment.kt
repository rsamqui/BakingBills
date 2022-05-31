package com.rsamqui.bakingbills.fragments.lista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.api.ApiService
import com.rsamqui.bakingbills.api.dataclass.Productos
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.bd.adapters.ProductoAdapter
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentProductosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductosFragment : Fragment() {

    private lateinit var API: ApiService
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

        setHasOptionsMenu(true)
        API = Common.retrofitService
        return fBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternet()
        setupViews()
    }

    private fun checkInternet() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            try {
                if(isConnected) {
                    searchAllProducts()
                } else{
                    Toast.makeText(requireContext(), "No hay conexión a Internet", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {

            }

        }
    }

    private fun searchAllProducts() {
        var list: ArrayList<Productos>
        CoroutineScope(Dispatchers.IO).launch {
            var count:  Int = 0
            val call = API.getAllProductos()

            list = call

            list.forEach{_ ->
                run {
                    for (i in 0..list.lastIndex) {
                        var id: Int = (list[i].idProduct ?: Int) as Int
                        var name: String = list[i].nombreP.toString()
                        var description: String = list[i].descripcion.toString()
                        var quantity: Double = (list[i].cantidad ?: Double) as Double
                        var price: Double = (list[i].precio ?: Double) as Double
                        var weight: Double = (list[i].peso ?: Double) as Double

                        val producto = ProductoEntity(id, name, description, quantity, price, weight)
                        count++

                        viewModel.agregarProducto(producto)
                    }
                }
                if (count == list.size){
                    return@launch
                }
            }
        }
    }

    private fun setupViews() {
        with(fBinding) {
            addProduct.setOnClickListener {

                it.findNavController().navigate(R.id.products_to_add_products)
            }
        }
    }
}