package com.rsamqui.bakingbills.bd.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.repository.ProductoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoViewModels(application:
                        Application):AndroidViewModel(application) {
    val lista : LiveData<List<ProductoEntity>>
    private val repository: ProductoRepository
    init {
        val productoDao =
            BDPanaderia.getDataBase(application).productoDao()
        repository = ProductoRepository(productoDao)
        lista = repository.listado
    }
    fun agregarProducto(producto: ProductoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProducto(producto)
        }
    }
    fun actualizarProducto(producto: ProductoEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateProducto(producto)
        }
    }
    fun eliminarProducto(producto: ProductoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProducto(producto)
        }
    }
    fun eliminarTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}
