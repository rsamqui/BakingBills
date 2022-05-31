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
import com.rsamqui.bakingbills.api.dataclass.Presupuesto
import com.rsamqui.bakingbills.api.network.Common
import com.rsamqui.bakingbills.api.network.NetworkConnection
import com.rsamqui.bakingbills.bd.adapters.PresupuestoAdapter
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.viewmodels.PresupuestoViewModels
import com.rsamqui.bakingbills.databinding.FragmentPresupuestoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PresupuestoFragment : Fragment() {

    private lateinit var API: ApiService
    lateinit var fBinding: FragmentPresupuestoBinding
    private lateinit var viewModel: PresupuestoViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentPresupuestoBinding.inflate(layoutInflater)
        val adapter = PresupuestoAdapter()
        val recycleView = fBinding.rcvListaPresupuesto
        recycleView.adapter = adapter
        recycleView.layoutManager =
            LinearLayoutManager(requireContext())
        viewModel =
            ViewModelProvider(this).get(PresupuestoViewModels::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        { presupuesto ->
            adapter.setData(presupuesto)
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
                if (isConnected) {
                    searchAllPresupuestos()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "No hay conexión a Internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {

            }

        }
    }

    private fun searchAllPresupuestos() {
        var list: ArrayList<Presupuesto>
        CoroutineScope(Dispatchers.IO).launch {
            var count: Int = 0
            val call = API.getAllPresupuesto()

            list = call

            list.forEach { _ ->
                run {
                    for (i in 0..list.lastIndex) {
                        var id: Int = (list[i].idPresupuesto ?: Int) as Int
                        var ingredient: String = list[i].ingrediente.toString()
                        var units: Double = (list[i].unidades ?: Double) as Double
                        var measure: String = list[i].medida.toString()
                        var price: Double = (list[i].precio ?: Double) as Double
                        var total: Double = (list[i].total ?: Double) as Double

                        val budget = PresupuestoEntity(id, ingredient, units, measure, price, total)
                        count++

                        viewModel.agregarPresupuesto(budget)
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
            addBudget.setOnClickListener {

                it.findNavController().navigate(R.id.budget_to_add_budget)
            }
        }
    }
}