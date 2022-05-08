package com.rsamqui.bakingbills.bd.repository

import androidx.lifecycle.LiveData
import com.rsamqui.bakingbills.bd.dao.IngredienteDao
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity

class IngredienteRepository(private val dao: IngredienteDao) {
<<<<<<< HEAD
    val listado : LiveData<List<IngredienteEntity>> = dao.getAllIngrediente()

    suspend fun addIngrediente(ingrediente: IngredienteEntity){
        dao.insertIngrediente(ingrediente)
    }
    suspend fun updateIngrediente(ingrediente: IngredienteEntity){
        dao.update(ingrediente)
    }
    suspend fun deleteIngrediente(ingrediente: IngredienteEntity){
        dao.delete(ingrediente)
    }
    suspend fun deleteAll(){
=======
    val listado: LiveData<List<IngredienteEntity>> =
        dao.getAllRealData()

    suspend fun addIngrediente(ingrediente: IngredienteEntity) {
        dao.insert(ingrediente)
    }

    suspend fun updateIngrediente(ingrediente: IngredienteEntity) {
        dao.update(ingrediente)
    }

    suspend fun deleteIngrediente(ingrediente: IngredienteEntity) {
        dao.delete(ingrediente)
    }

    suspend fun deleteAll() {
>>>>>>> master
        dao.deleteAll()
    }
}