package com.rsamqui.bakingbills.bd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.databinding.ItemProductoBinding
import com.rsamqui.bakingbills.fragments.editar.editProductoFragmentDirections
import com.rsamqui.bakingbills.fragments.lista.ProductosFragmentDirections
import com.rsamqui.bakingbills.fragments.lista.UsuariosFragmentDirections

class ProductoAdapter : RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    private var listadoProducto = emptyList<ProductoEntity>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType:
        Int
    ): ProductoHolder {
        val binding =

            ItemProductoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )

        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.bind(
            listadoProducto[position]
        )
    }


    override fun getItemCount(): Int = listadoProducto.size

    fun setData(products: List<ProductoEntity>) {
        this.listadoProducto = products
        notifyDataSetChanged()
    }

    inner class ProductoHolder(val binding: ItemProductoBinding)
        :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: ProductoEntity) {
            with(binding) {
                tvId.text = producto.idProducto.toString()
                tvNombre.text = producto.nombre
                tvDescripcion.text = producto.descripcion
                tvPrecio.text = producto.precio.toString()
                tvPeso.text = producto.peso.toString()
                cvProducto.setOnClickListener {
                    val action =
                        ProductosFragmentDirections.productsToEditProducts(producto)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

}
