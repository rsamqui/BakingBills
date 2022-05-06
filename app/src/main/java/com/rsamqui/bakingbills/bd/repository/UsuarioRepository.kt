package com.rsamqui.bakingbills.bd.repository

import androidx.lifecycle.LiveData
import com.rsamqui.bakingbills.bd.dao.UsuarioDao
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {
    val listado: LiveData<List<UsuarioEntity>> =
        dao.getAllRealData()

    suspend fun addUsuario(usuario: UsuarioEntity) {
        dao.insert(usuario)
    }

    suspend fun updateUsuario(usuario: UsuarioEntity) {
        dao.update(usuario)
    }

    suspend fun deleteUsuario(usuario: UsuarioEntity) {
        dao.delete(usuario)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}