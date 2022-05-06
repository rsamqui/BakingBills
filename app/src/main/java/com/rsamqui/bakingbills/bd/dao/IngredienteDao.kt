package com.rsamqui.bakingbills.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity

@Dao
interface IngredienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingrediente: IngredienteEntity)

    @Query("SELECT * FROM Ingrediente")
    suspend fun getAll(): List<IngredienteEntity>

    @Query("SELECT * FROM Ingrediente")
    fun getAllRealData(): LiveData<List<IngredienteEntity>>

    @Query("SELECT * FROM Ingrediente WHERE idIngrediente = :id")
    suspend fun getById(id : Int) : IngredienteEntity

    @Update
    suspend fun update(ingrediente: IngredienteEntity)

    @Delete
    suspend fun delete(ingrediente: IngredienteEntity)

    @Query("Delete from Ingrediente")
    suspend fun deleteAll()


}