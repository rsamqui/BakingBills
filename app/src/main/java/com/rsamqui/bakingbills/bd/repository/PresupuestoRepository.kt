package com.rsamqui.bakingbills.bd.repository

import androidx.lifecycle.LiveData
import com.rsamqui.bakingbills.bd.dao.PresupuestoDao
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity

class PresupuestoRepository(private val dao: PresupuestoDao) {
    val listado: LiveData<List<PresupuestoEntity>> =
        dao.getAllRealData()

    suspend fun addPresupuesto(presupuesto: PresupuestoEntity){
        dao.insert(presupuesto)
    }

    suspend fun updatePresupuesto(presupuesto: PresupuestoEntity) {
        dao.update(presupuesto)
    }

    suspend fun deletePresupuesto(presupuesto: PresupuestoEntity) {
        dao.delete(presupuesto)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}