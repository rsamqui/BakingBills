package com.rsamqui.bakingbills.fragments.agregar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rsamqui.bakingbills.API.ApiService
import com.rsamqui.bakingbills.API.Network.Common
import com.rsamqui.bakingbills.API.Network.NetworkConnection
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.viewmodels.IngredienteViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddIngredientesBinding
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.dao.IngredienteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception

class addIngredientsFragment : Fragment() {
    private lateinit var API: ApiService
    lateinit var fBinding: FragmentAddIngredientesBinding
    private lateinit var viewModel: IngredienteViewModels
    private var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fBinding = FragmentAddIngredientesBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(IngredienteViewModels::class.java)
        checkInternet()
        fBinding.btnAgregar.setOnClickListener {
            guardarRegistroOffline()
        }
        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_ingredientes_to_ingredientes)
        }
        API = Common.retrofitService
        count = 0
        return fBinding.root
    }



    private fun guardarRegistro() {
        val name = fBinding.etNombre.text.toString()
        val quantity = fBinding.etMedida.text.toString()
        val price = fBinding.etPrecio.text.toString()

        val jsonObject = JSONObject()
        jsonObject.put("idIngrediente", id)
        jsonObject.put("nombre", "$name")
        jsonObject.put("cantidad", quantity)
        jsonObject.put("precio", price)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            if (fBinding.etNombre.text.isNotEmpty() && fBinding.etMedida.text.isNotEmpty() && fBinding.etPrecio.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    if(count == 0) {
                        API.addIngrediente(requestBody)
                    }

                    var ingrediente =
                        IngredienteEntity(0, name, quantity.toDouble(), price.toDouble())

                    viewModel.agregarIngrediente(ingrediente)
                }
                Toast.makeText(
                    requireContext(), "Registro guardado",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.add_ingredientes_to_ingredientes)
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

    fun checkInternet(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val BD: BDPanaderia = BDPanaderia.getInstance(requireContext().applicationContext)
                val daoI: IngredienteDao = BD.ingredienteDao()

                CoroutineScope(Dispatchers.IO).launch {
                    var ingredientes: List<IngredienteEntity> = daoI.getAll()
                    var ingredientesApi = API.getAllIngredientes()
                    var ingredientesSize = ingredientes.size
                    var ingredientesApiSize = ingredientesApi.size

                    if (ingredientesSize > ingredientesApiSize) {
                        for (i in 0..ingredientes.lastIndex) {
                            var id: Int = ingredientes[i].idIngrediente
                            var name: String = ingredientes[i].nombre
                            var quantity: Double = ingredientes[i].cantidad
                            var price: Double = ingredientes[i].precio

                            val jsonObject = JSONObject()
                            jsonObject.put("idIngrediente", id)
                            jsonObject.put("nombre", "$name")
                            jsonObject.put("cantidad", quantity)
                            jsonObject.put("precio", price)

                            val jsonObjectString = jsonObject.toString()
                            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                            var ingrediente = IngredienteEntity(id, name, quantity, price)
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.eliminarIngrediente(ingrediente)

                                API.addIngrediente(requestBody)
                            }
                        }
                    }

                }
            }
            else {
                Toast.makeText(requireContext(),"No hay conexión a Internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarRegistroOffline() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            try {
                if(isConnected) {
                    guardarRegistro()
                    count = 4
                } else {
                    val nombre = fBinding.etNombre.text.toString()
                    val cantidad = fBinding.etMedida.text.toString()
                    val precio = fBinding.etPrecio.text.toString()

                    var ingrediente = IngredienteEntity(0, nombre, cantidad.toDouble(), precio.toDouble())
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.agregarIngrediente(ingrediente)
                    }
                    Toast.makeText(requireContext(), "Guardado localmente", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.add_ingredientes_to_ingredientes)

                    Toast.makeText(
                        requireContext(),"No hay conexión a Internet", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

            }
        }
    }
}