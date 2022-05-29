package com.rsamqui.bakingbills.fragments.editar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rsamqui.bakingbills.API.DataClass.Ingredientes
import com.rsamqui.bakingbills.API.ApiService
import com.rsamqui.bakingbills.API.Network.Common
import com.rsamqui.bakingbills.API.Network.NetworkConnection
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.viewmodels.IngredienteViewModels
import com.rsamqui.bakingbills.databinding.FragmentEditIngredientesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class editIngredientesFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentEditIngredientesBinding
    private val args by navArgs<editIngredientesFragmentArgs>()
    private lateinit var viewModel: IngredienteViewModels

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        fBinding = FragmentEditIngredientesBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(IngredienteViewModels::class.java)
        with(fBinding) { etNombre.setText(args.currentIngrediente.nombre)
            etMedida.setText(args.currentIngrediente.cantidad.toString())
            etPrecio.setText(args.currentIngrediente.precio.toString())
            btnEditar.setOnClickListener {
                checkInternet()
            }
            fBinding.btnVolver.setOnClickListener {
                findNavController().navigate(R.id.edit_ingredientes_to_ingredientes)
            }
            fBinding.deleteIngrediente.setOnClickListener{
                checkInternetDel()
            }
        }
        setHasOptionsMenu(true)
        API = Common.retrofitService
        return fBinding.root
    }

    private fun GuardarCambios() {
        val name = fBinding.etNombre.text.toString()
        val quantity = fBinding.etMedida.text.toString()
        val price = fBinding.etPrecio.text.toString()
        val id = args.currentIngrediente.idIngrediente

        val jsonObject = JSONObject()
        jsonObject.put("idIngrediente", id)
        jsonObject.put("nombre", name)
        jsonObject.put("cantidad", quantity)
        jsonObject.put("precio", price)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            if (fBinding.etNombre.text.isNotEmpty() && fBinding.etMedida.text.isNotEmpty() && fBinding.etPrecio.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    API.editIngrediente(requestBody)
                }
                var ingrediente = IngredienteEntity(id, name, quantity.toDouble(), price.toDouble())
                viewModel.actualizarIngrediente(ingrediente)
                Toast.makeText(
                    requireContext(), "Registro actualizado",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.edit_ingredientes_to_ingredientes)
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

    override fun onCreateOptionsMenu(
        menu: Menu, inflater:
        MenuInflater
    ) {
        inflater.inflate(R.menu.menuopcion, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar) {
            delIngrediente()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkInternet(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if(isConnected) {
                GuardarCambios()
            } else{
                Toast.makeText(requireContext(), "No hay conexión a Internet, no puede editar ni eliminar datos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkInternetDel(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if(isConnected) {
                delIngrediente()
            } else{
                Toast.makeText(requireContext(), "No hay conexión a Internet, no puede editar ni eliminar datos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun ApiDelIngrediente(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            API.delIngrediente(id)
        }
    }

    private fun delIngrediente() {
        val name = fBinding.etNombre.text.toString()
        val quantity = fBinding.etMedida.text.toString()
        val price = fBinding.etPrecio.text.toString()

        val jsonObject = JSONObject()
        jsonObject.put("idIngrediente", id)
        jsonObject.put("nombre", "$name")
        jsonObject.put("cantidad", quantity)
        jsonObject.put("precio", price)

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            var ingrediente = IngredienteEntity(args.currentIngrediente.idIngrediente, name, quantity.toDouble(), price.toDouble())
                viewModel.eliminarIngrediente(ingrediente)
                ApiDelIngrediente(args.currentIngrediente.idIngrediente)
            Toast.makeText(
                requireContext(),
                "Registro eliminado",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.edit_ingredientes_to_ingredientes)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Eliminación cancelada",
                Toast.LENGTH_SHORT
            ).show()
        }
        alerta.setTitle("Eliminando ${args.currentIngrediente.nombre}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentIngrediente.nombre}?")
        alerta.create().show()
    }
}