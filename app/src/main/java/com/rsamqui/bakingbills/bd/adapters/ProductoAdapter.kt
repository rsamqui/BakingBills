package com.rsamqui.bakingbills.bd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.adapters.ProductoAdapter.*
import com.rsamqui.bakingbills.bd.entidades.ProductoItem

class ProductoAdapter (val productoLista: List<ProductoItem>): RecyclerView.Adapter<ProductoHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductoHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, null, false)
        return ProductoHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        val current = productoLista[position]
        holder.
    }

    override fun getItemCount(): Int = productoLista.size

    class ProductoHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvPeso: TextView = itemView.findViewById(R.id.tvPeso)
    }

}
