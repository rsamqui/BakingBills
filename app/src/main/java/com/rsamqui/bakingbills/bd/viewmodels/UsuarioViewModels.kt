package com.rsamqui.bakingbills.bd.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity
import com.rsamqui.bakingbills.bd.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModels(application:
                        Application):AndroidViewModel(application) {
    val lista : LiveData<List<UsuarioEntity>>
    private val repository: UsuarioRepository
    init {
        val usuarioDao =
            BDPanaderia.getDataBase(application).usuarioDao()
        repository = UsuarioRepository(usuarioDao)
        lista = repository.listado
    }
    fun agregarUsuario(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUsuario(usuario)
        }
    }
    fun actualizarUsuario(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUsuario(usuario)
        }
    }
    fun eliminarUsuario(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUsuario(usuario)
        }
    }
    fun eliminarTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}
