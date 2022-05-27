package com.rsamqui.bakingbills.fragments.lista

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsamqui.bakingbills.API.ApiService
import com.rsamqui.bakingbills.API.DataClass.Ingredientes
import com.rsamqui.bakingbills.API.Network.Common
import com.rsamqui.bakingbills.API.Network.NetworkConnection
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.adapters.IngredienteAdapter
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.viewmodels.IngredienteViewModels
import com.rsamqui.bakingbills.databinding.FragmentIngredientesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IngredientesFragment : Fragment() {
    lateinit var fBinding: FragmentIngredientesBinding

    private lateinit var API: ApiService
    private lateinit var viewModel : IngredienteViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        fBinding = FragmentIngredientesBinding.inflate(layoutInflater)
        val adapter = IngredienteAdapter()
        val recycleView = fBinding.RcvIngrediente
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this).get(IngredienteViewModels::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        {
                ingrediente -> adapter.setData(ingrediente)
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

    private fun setupViews() {
        with(fBinding) {
            BtnAgregarIngrediente.setOnClickListener {
                it.findNavController().navigate(R.id.ingredientes_to_add_ingredientes)
            }
        }
    }

    private fun checkInternet(){
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if(isConnected) {
                searchAllIngredientes()
            } else{
                Toast.makeText(requireContext(), "No hay conexión a Internet", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun searchAllIngredientes() {
        var list: ArrayList<Ingredientes>
        CoroutineScope(Dispatchers.IO).launch {
            var count:  Int = 0
            val call = API.getAllIngredientes()

            list = call

            list.forEach{_ ->
                run {
                    for (i in 0..list.lastIndex) {
                        var id: Int = (list[i].idIngredient ?: Int) as Int
                        var name: String = list[i].nombre.toString()
                        var quantity: Double = (list[i].cantidad ?: Double) as Double
                        var price: Double = (list[i].precio ?: Double) as Double

                        val ingrediente = IngredienteEntity(id, name, quantity, price)
                        count++

                        viewModel.agregarIngrediente(ingrediente)
                    }
                }
                if (count == list.size){
                    return@launch
                }
            }
        }
    }
}