package com.rsamqui.bakingbills.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity

@Dao
interface PresupuestoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(presupuesto: PresupuestoEntity)

    @Query("SELECT * FROM Presupuesto")
    suspend fun getAll(): List<PresupuestoEntity>

    @Query("SELECT * FROM Presupuesto")
    fun getAllRealData(): LiveData<List<PresupuestoEntity>>

    @Query("SELECT * FROM Presupuesto WHERE idPresupuesto = :id")
    suspend fun getById(id: Int): PresupuestoEntity

    @Update
    suspend fun update(presupuesto: PresupuestoEntity)

    @Delete
    suspend fun delete(presupuesto: PresupuestoEntity)

    @Query("DELETE FROM Presupuesto")
    suspend fun deleteAll()

}