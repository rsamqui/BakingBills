package com.rsamqui.bakingbills.bd.repositories

import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import kotlinx.coroutines.flow.Flow

interface ProductoRepository {

    suspend fun insertProduct(producto: ProductoEntity)

    suspend fun getAll(): Flow<List<ProductoEntity>>

    suspend fun getById(id: Int): ProductoEntity

    suspend fun update(producto: ProductoEntity)

    suspend fun delete(id: Int)
}
