package com.rsamqui.bakingbills.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioEntity)

    @Query("SELECT * FROM Usuario")
    suspend fun getAll(): List<UsuarioEntity>

    @Query("SELECT * FROM Usuario")
    fun getAllRealData(): LiveData<List<UsuarioEntity>>

    @Query("SELECT * FROM Usuario WHERE idUsuario = :id")
    suspend fun getById(id : Int) : UsuarioEntity

    @Update
    suspend fun update(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Query("Delete from Usuario")
    suspend fun deleteAll()


}