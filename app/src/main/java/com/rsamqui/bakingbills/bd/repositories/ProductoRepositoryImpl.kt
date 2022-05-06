package com.rsamqui.bakingbills.bd.repositories

import com.rsamqui.bakingbills.bd.dao.ProductoDao
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import kotlinx.coroutines.flow.Flow

class ProductoRepositoryImpl private constructor(
    private val productoDao: ProductoDao
): ProductoRepository{

    companion object{
        @Volatile
        private var INSTANCE : ProductoRepository? = null

        private fun createInstance() : ProductoRepository =
            ProductoRepositoryImpl()

        operator fun invoke() : ProductoRepository =
            INSTANCE ?: synchronized(this) {
                createInstance()
            }.also{INSTANCE = it}
    }

    override suspend fun insertProduct(producto: ProductoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Flow<List<ProductoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Int): ProductoEntity {
        TODO("Not yet implemented")
    }

    override suspend fun update(producto: ProductoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}