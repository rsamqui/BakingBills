package com.rsamqui.bakingbills.bd.repository

import androidx.lifecycle.LiveData
import com.rsamqui.bakingbills.bd.dao.ProductoDao
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity

class ProductoRepository(private val dao: ProductoDao) {
    val listado: LiveData<List<ProductoEntity>> =
        dao.getAllRealData()

    suspend fun addProducto(producto: ProductoEntity) {
        dao.insert(producto)
    }

    suspend fun updateProducto(producto: ProductoEntity) {
        dao.update(producto)
    }

    suspend fun deleteProducto(producto: ProductoEntity) {
        dao.delete(producto)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}