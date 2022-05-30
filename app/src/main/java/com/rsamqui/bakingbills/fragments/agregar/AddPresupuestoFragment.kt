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
import com.rsamqui.bakingbills.bd.dao.PresupuestoDao
import com.rsamqui.bakingbills.bd.dao.ProductoDao
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.viewmodels.PresupuestoViewModels
import com.rsamqui.bakingbills.databinding.FragmentAddPresupuestoBinding
import kotlinx.android.synthetic.main.fragment_add_presupuesto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception

class AddPresupuestoFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentAddPresupuestoBinding
    private lateinit var viewModel: PresupuestoViewModels
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fBinding =
            FragmentAddPresupuestoBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(PresupuestoViewModels::class.java)
        checkInternet()

        fBinding.btnAgregar.setOnClickListener {
            guardarPresupuestoOffline()
        }

        fBinding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.add_budget_to_budget)
        }

        calcularTotal()

        API = Common.retrofitService
        count = 0
        return fBinding.root
    }

    private fun guardarRegistro() {
        val ingredients = fBinding.etIngrediente.text.toString()
        val units = fBinding.etUnits.text.toString()
        val measure = fBinding.etMedida.text.toString()
        val price = fBinding.etPrice.text.toString()
        val total = fBinding.etPrecioT.text.toString()

        val jsonObject = JSONObject()
        jsonObject.put("idPresupuesto", id)
        jsonObject.put("ingrediente", "$ingredients")
        jsonObject.put("unidades", units)
        jsonObject.put("medida", measure)
        jsonObject.put("precio", price)
        jsonObject.put("total", total)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            if (fBinding.etIngrediente.text.isNotEmpty() && fBinding.etUnits.text.isNotEmpty()
                && fBinding.etMedida.text.isNotEmpty() && fBinding.etPrice.text.isNotEmpty()
                && fBinding.etPrecioT.text.isNotEmpty()
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (count == 0) {
                        API.addPresupuesto(requestBody)
                    }

                    var presupuesto =
                        PresupuestoEntity(
                            0,
                            ingredients,
                            units.toDouble(),
                            measure,
                            price.toDouble(),
                            total.toDouble()
                        )

                    viewModel.agregarPresupuesto(presupuesto)
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
                val daoB: PresupuestoDao = BD.presupuestoDao()

                CoroutineScope(Dispatchers.IO).launch {
                    var presupuestos: List<PresupuestoEntity> = daoB.getAll()
                    var presupuestosApi = API.getAllPresupuesto()
                    var presupuestosSize = presupuestos.size
                    var presupuestosApiSize = presupuestosApi.size

                    if (presupuestosSize > presupuestosApiSize) {
                        for (i in 0..presupuestos.lastIndex) {
                            var id: Int = presupuestos[i].idPresupuesto
                            var ingredients = presupuestos[i].ingrediente
                            var units = presupuestos[i].unidades
                            var measure = presupuestos[i].medida
                            var price = presupuestos[i].precio
                            var total = presupuestos[i].total

                            val jsonObject = JSONObject()
                            jsonObject.put("idPresupuesto", id)
                            jsonObject.put("ingrediente", "$ingredients")
                            jsonObject.put("unidades", units)
                            jsonObject.put("medida", measure)
                            jsonObject.put("precio", price)
                            jsonObject.put("total", total)

                            val jsonObjectString = jsonObject.toString()
                            val requestBody =
                                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                            var presupuesto =
                                PresupuestoEntity(
                                    0,
                                    ingredients,
                                    units,
                                    measure,
                                    price,
                                    total
                                )
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.eliminarPresupuesto(presupuesto)

                                API.addPresupuesto(requestBody)
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No hay conexión a Internet",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

        }
    }

    private fun calcularTotal() {
        fBinding.btnCalcular.setOnClickListener {
            val cant = (etUnits.text.toString())
            val price = (etPrice.text.toString())

            if (cant.isNotEmpty() && price.isNotEmpty()) {

                val total = (cant.toDouble() * price.toDouble()).toString()
                etPrecioT.setText(total)
                Toast.makeText(
                    requireContext(), "Total calculado",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(), "Debe rellenar todos los campos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun guardarPresupuestoOffline() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            try {
                if (isConnected) {
                    guardarRegistro()
                    count = 4
                } else {
                    val ingrediente = fBinding.etIngrediente.text.toString()
                    val unidades = fBinding.etUnits.text.toString()
                    val medida = fBinding.etMedida.text.toString()
                    val precio = fBinding.etPrice.text.toString()
                    val total = fBinding.etPrecioT.text.toString()

                    val presupuesto = PresupuestoEntity(
                        0, ingrediente, unidades.toDouble(), medida, precio.toDouble(),
                        total.toDouble()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.agregarPresupuesto(presupuesto)
                        Toast.makeText(
                            requireContext(), "Registro Guardado Localmente",
                            Toast.LENGTH_LONG
                        ).show()

                        findNavController().navigate(R.id.add_budget_to_budget)

                        Toast.makeText(
                            requireContext(), "No hay conexion a internet",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            } catch (e: Exception) {

            }
        }
    }
}


