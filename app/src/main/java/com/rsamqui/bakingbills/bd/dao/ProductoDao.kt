package com.rsamqui.bakingbills.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: ProductoEntity)

    @Query("SELECT * FROM Producto")
    suspend fun getAll(): List<ProductoEntity>

    @Query("SELECT * FROM Producto")
    fun getAllRealData(): LiveData<List<ProductoEntity>>

    @Query("SELECT * FROM Producto WHERE idProducto = :id")
    suspend fun getById(id : Int) : ProductoEntity

    @Update
    suspend fun update(producto: ProductoEntity)

    @Delete
    suspend fun delete(producto: ProductoEntity)

    @Query("DELETE FROM Producto")
    suspend fun deleteAll()


}