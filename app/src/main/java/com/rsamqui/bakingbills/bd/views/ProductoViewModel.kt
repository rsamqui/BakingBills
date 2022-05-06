package com.rsamqui.bakingbills.bd.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.repositories.ProductoRepository
import com.rsamqui.bakingbills.bd.repositories.ProductoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoViewModel(private val productoRepository: ProductoRepository = ProductoRepositoryImpl.invoke()) :
    ViewModel() {

    private var _producto = MutableLiveData<List<ProductoEntity>>()
    var producto : LiveData<List<ProductoEntity>> = _producto
    init {
        getProd()
    }

    private fun getProd() {
        viewModelScope.launch (Dispatchers.IO) {
            productoRepository.getAll()
        }
    }
}