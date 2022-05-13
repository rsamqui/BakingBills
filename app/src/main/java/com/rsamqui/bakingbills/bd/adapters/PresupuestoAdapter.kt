package com.rsamqui.bakingbills.bd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.databinding.ItemPresupuestoBinding
import com.rsamqui.bakingbills.fragments.lista.PresupuestoFragmentDirections

class PresupuestoAdapter : RecyclerView.Adapter<PresupuestoAdapter.PresupuestoHolder>() {
    private var listadoPresupuesto = emptyList<PresupuestoEntity>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PresupuestoHolder {
        val binding =

            ItemPresupuestoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )

        return PresupuestoHolder(binding)
    }

    override fun onBindViewHolder(holder: PresupuestoHolder, position: Int) {
        holder.bind(
            listadoPresupuesto[position]
        )
    }

    override fun getItemCount(): Int = listadoPresupuesto.size

    fun setData(pres: List<PresupuestoEntity>) {
        this.listadoPresupuesto = pres
        notifyDataSetChanged()
    }


    inner class PresupuestoHolder(val binding: ItemPresupuestoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(presupuesto: PresupuestoEntity) {
            with(binding) {
                tvId.text = presupuesto.idPresupuesto.toString()
                tvIngrediente.text = presupuesto.ingrediente
                tvMedida.text = presupuesto.medida
                tvPrecioU.text = presupuesto.precio.toString()
                tvPrecioT.text = presupuesto.total.toString()
                cvPresupuesto.setOnClickListener{
                    val action =
                        PresupuestoFragmentDirections.budgetToEditBudget(presupuesto)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}