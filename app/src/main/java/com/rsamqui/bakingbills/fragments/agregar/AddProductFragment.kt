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
import com.rsamqui.bakingbills.api.ApiService
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.dao.ProductoDao
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception

class AddProductFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentAddProductBinding
    private lateinit var viewModel: ProductoViewModels
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fBinding =
            FragmentAddProductBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(ProductoViewModels::class.java)
        checkInternet()

        fBinding.btnAgregar.setOnClickListener {
            guardarProductoOffline()
        }

        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_products_to_products)
        }

        API = Common.retrofitService
        count = 0
        return fBinding.root
    }

    private fun guardarRegistro() {
        val name = fBinding.etNombre.text.toString()
        val description = fBinding.etDescripcion.text.toString()
        val quantity = fBinding.etCantidad.text.toString()
        val price = fBinding.etPrecio.text.toString()
        val weight = fBinding.etPeso.text.toString()

        val jsonObject = JSONObject()
        jsonObject.put("idProducto", id)
        jsonObject.put("nombre", "$name")
        jsonObject.put("descripcion", description)
        jsonObject.put("cantidad", quantity)
        jsonObject.put("precio", price)
        jsonObject.put("peso", weight)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            if (fBinding.etNombre.text.isNotEmpty() && fBinding.etDescripcion.text.isNotEmpty()
                && fBinding.etCantidad.text.isNotEmpty() && fBinding.etPrecio.text.isNotEmpty()
                && fBinding.etPeso.text.isNotEmpty()
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (count == 0) {
                        API.addIngrediente(requestBody)
                    }

                    var producto =
                        ProductoEntity(
                            0,
                            name,
                            description,
                            quantity.toDouble(),
                            price.toDouble(),
                            weight.toDouble()
                        )

                    viewModel.agregarProducto(producto)
                }
                Toast.makeText(
                    requireContext(), "Registro guardado",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.add_products_to_products)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Debe rellenar todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {

        }
    }

    private fun checkInternet() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val BD: BDPanaderia = BDPanaderia.getInstance(requireContext().applicationContext)
                val daoP: ProductoDao = BD.productoDao()

                CoroutineScope(Dispatchers.IO).launch {
                    var productos: List<ProductoEntity> = daoP.getAll()
                    var productosApi = API.getAllProductos()
                    var productosSize = productos.size
                    var productosApiSize = productosApi.size

                    if (productosSize > productosApiSize) {
                        for (i in 0..productos.lastIndex) {
                            var id: Int = productos[i].idProducto
                            var name: String = productos[i].nombre
                            var description: String = productos[i].descripcion
                            var quantity: Double = productos[i].cantidad
                            var price: Double = productos[i].precio
                            var weight: Double = productos[i].peso

                            val jsonObject = JSONObject()
                            jsonObject.put("idProducto", id)
                            jsonObject.put("nombre", "$name")
                            jsonObject.put("descripcion", description)
                            jsonObject.put("cantidad", quantity)
                            jsonObject.put("precio", price)
                            jsonObject.put("peso", weight)

                            val jsonObjectString = jsonObject.toString()
                            val requestBody =
                                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                            var producto =
                                ProductoEntity(
                                    id,
                                    name,
                                    description,
                                    quantity,
                                    price,
                                    weight
                                )
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.eliminarProducto(producto)

                                API.addIngrediente(requestBody)
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun guardarProductoOffline() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            try {
                if (isConnected) {
                    guardarRegistro()
                    count = 4
                } else {

                    val nombre = fBinding.etNombre.text.toString()
                    val descripcion = fBinding.etDescripcion.text.toString()
                    val cantidad = fBinding.etCantidad.text.toString()
                    val precio = fBinding.etPrecio.text.toString()
                    val peso = fBinding.etPeso.text.toString()

                    if (nombre.isNotEmpty() && descripcion.isNotEmpty() && cantidad.isNotEmpty() &&
                        precio.isNotEmpty() && peso.isNotEmpty()
                    ) {

                        val producto = ProductoEntity(
                            0, nombre, descripcion, cantidad.toDouble(), precio.toDouble(),
                            peso.toDouble()
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.agregarProducto(producto)

                            Toast.makeText(
                                requireContext(), "Registro Guardado Localmente",
                                Toast.LENGTH_LONG
                            ).show()

                            findNavController().navigate(R.id.add_products_to_products)

                            Toast.makeText(
                                requireContext(), "No hay conexion a internet",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }catch (e: Exception){

            }
        }
    }
}


