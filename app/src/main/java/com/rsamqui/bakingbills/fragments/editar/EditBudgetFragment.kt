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
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.viewmodels.PresupuestoViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditBudgetBinding
import kotlinx.android.synthetic.main.fragment_edit_budget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class EditBudgetFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentEditBudgetBinding
    private val args by navArgs<EditBudgetFragmentArgs>()
    private lateinit var viewModel: PresupuestoViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fBinding =
            FragmentEditBudgetBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this).get(PresupuestoViewModels::class.java)
        with(fBinding) {

            tvID.text = args.currentBudget.idPresupuesto.toString()
            etIngrediente.setText(args.currentBudget.ingrediente)
            etUnidades.setText(args.currentBudget.unidades.toString())
            etMedida.setText(args.currentBudget.medida)
            etPrecio.setText(args.currentBudget.precio.toString())
            etPrecioT.setText(args.currentBudget.total.toString())

            btnEdit.setOnClickListener {
                checkInternet()
            }

            fBinding.btnVolver.setOnClickListener {
                findNavController().navigate(R.id.edit_budget_to_budget)
            }

            fBinding.deleteBudget.setOnClickListener {
                checkInternetDel()
            }

            fBinding.btnEdit.setOnClickListener {
                asegurarEdicion()
            }

            calcularTotal()

        }
        API = Common.retrofitService
        setHasOptionsMenu(true)
        return fBinding.root
    }

    private fun updateBudget() {
        val ingredient = fBinding.etIngrediente.text.toString()
        val units = fBinding.etUnidades.text.toString()
        val measure = fBinding.etMedida.text.toString()
        val price = fBinding.etPrecio.text.toString()
        val total = fBinding.etPrecioT.text.toString()
        val id = args.currentBudget.idPresupuesto

        val jsonObject = JSONObject()
        jsonObject.put("idPresupuesto", id)
        jsonObject.put("ingrediente", "$ingredient")
        jsonObject.put("unidades", units)
        jsonObject.put("medida", measure)
        jsonObject.put("precio", price)
        jsonObject.put("total", total)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            if (fBinding.etIngrediente.text.isNotEmpty() && fBinding.etUnidades.text.isNotEmpty()
                && fBinding.etMedida.text.isNotEmpty() && fBinding.etPrecio.text.isNotEmpty()
                && fBinding.etPrecioT.text.isNotEmpty()
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    API.editPresupuesto(requestBody)
                }

                var budget = PresupuestoEntity(
                    id,
                    ingredient,
                    units.toDouble(),
                    measure,
                    price.toDouble(),
                    total.toDouble()
                )

                viewModel.actualizarPresupuesto(budget)
                Toast.makeText(
                    requireContext(), "Registro actualizado",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.edit_budget_to_budget)
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

    private fun checkInternetDel() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                delBudget()
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay conexión a Internet, no puede editar ni eliminar datos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkInternet() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                delBudget()
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay conexión a Internet, no puede editar ni eliminar datos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun ApiDelBudget(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            API.delPresupuesto(id)
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
            delBudget()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delBudget() {
        val ingredient = fBinding.etIngrediente.text.toString()
        val units = fBinding.etUnidades.text.toString()
        val measure = fBinding.etMedida.text.toString()
        val price = fBinding.etPrecio.text.toString()
        val total = fBinding.etPrecioT.text.toString()


        val jsonObject = JSONObject()
        jsonObject.put("idPresupuesto", id)
        jsonObject.put("ingrediente", "$ingredient")
        jsonObject.put("unidades", units)
        jsonObject.put("medida", measure)
        jsonObject.put("precio", price)
        jsonObject.put("total", total)

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->

            var budget = PresupuestoEntity(
                args.currentBudget.idPresupuesto,
                ingredient,
                units.toDouble(),
                measure,
                price.toDouble(),
                total.toDouble()
            )

            viewModel.eliminarPresupuesto(budget)
            ApiDelBudget(args.currentBudget.idPresupuesto)
            Toast.makeText(
                requireContext(),
                "Registro eliminado satisfactoriamente...",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.edit_budget_to_budget)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando ${args.currentBudget.ingrediente}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentBudget.ingrediente}?")
        alerta.create().show()
    }

    private fun asegurarEdicion() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setPositiveButton("He terminado") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Regsitro editado satisfactoriamente...",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.edit_budget_to_budget)
        }
        alert.setNegativeButton("Volver a edicion") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alert.setTitle("Editando ${args.currentBudget.ingrediente}")
        alert.setMessage("¿Ha terminado de editar el presupuesto de ${args.currentBudget.ingrediente}? ¡Recuerda recalcular el precio total si editaste unidades o precio!")
        alert.create().show()
    }

    private fun calcularTotal() {
        fBinding.btnCalcular.setOnClickListener {
            val cant = (etUnidades.text.toString())
            val price = (etPrecio.text.toString())

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
}