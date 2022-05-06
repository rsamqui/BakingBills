package com.rsamqui.bakingbills.bd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.databinding.FragmentItemIngredienteBinding
import com.rsamqui.bakingbills.navigation.IngredientesFragmentDirections

class IngredienteAdapter : RecyclerView.Adapter<IngredienteAdapter.IngredienteHolder>() {

    private var listadoIngrediente = emptyList<IngredienteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteHolder {
        val binding = FragmentItemIngredienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredienteHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredienteHolder, position: Int) {
        holder.bind( listadoIngrediente[position] )
    }

    override fun getItemCount(): Int = listadoIngrediente.size

    fun setData(users: List<IngredienteEntity>) {
        this.listadoIngrediente = users
        notifyDataSetChanged()
    }

    inner class IngredienteHolder(val binding: FragmentItemIngredienteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingrediente: IngredienteEntity) {
            with(binding)
            {
                TvIdIngrediente.text = ingrediente.idIngrediente.toString()
                TvNombre.text = ingrediente.nombre
                TvCantidad.text = ingrediente.cantidad.toString()
                TvPrecio.text = ingrediente.precio.toString()

                IngredienteCV.setOnClickListener {
                    val action = IngredientesFragmentDirections.ingredientesToAddIngredientes()
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}