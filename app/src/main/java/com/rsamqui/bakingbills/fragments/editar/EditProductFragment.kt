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
import com.rsamqui.bakingbills.api.ApiService
import com.rsamqui.bakingbills.api.dataclass.Productos
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.viewmodels.ProductoViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditProductoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class EditProductFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentEditProductoBinding
    private val args by navArgs<EditProductFragmentArgs>()
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
                checkInternet()
            }

            fBinding.btnVolver.setOnClickListener {
                findNavController().navigate(R.id.edit_products_to_products)
            }

            fBinding.deleteBudget.setOnClickListener {
                checkInternetDel()
            }
        }
        setHasOptionsMenu(true)
        API = Common.retrofitService
        return fBinding.root
    }

    private fun updateProduct() {
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
                    API.editProducto(requestBody)
                }

                var productos = ProductoEntity(
                    id,
                    name,
                    description,
                    quantity.toDouble(),
                    price.toDouble(),
                    weight.toDouble()
                )
                viewModel.actualizarProducto(productos)
                Toast.makeText(
                    requireContext(), "Registro actualizado",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.edit_products_to_products)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Debe rellenar todos los campos",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        } catch (e: Exception) {

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
            delProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkInternet() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                updateProduct()
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay conexión a Internet, no puede editar ni eliminar datos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkInternetDel() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                delProduct()
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay conexión a Internet, no puede editar ni eliminar datos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun ApiDelProduct(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            API.delProducto(id)
        }
    }

    private fun delProduct() {
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

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->

            var productos = ProductoEntity(
                args.currentProduct.idProducto,
                name,
                description,
                quantity.toDouble(),
                price.toDouble(),
                weight.toDouble()
            )
            viewModel.eliminarProducto(productos)
            ApiDelProduct(args.currentProduct.idProducto)
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

