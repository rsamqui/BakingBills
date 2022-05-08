package com.rsamqui.bakingbills.bd.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
<<<<<<< HEAD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.repository.IngredienteRepository

class IngredienteViewModels(application: Application):AndroidViewModel(application) {
    val lista : LiveData<List<IngredienteEntity>>
    private val repository: IngredienteRepository

    init {
        val ingredienteDao = BDPanaderia.getDataBase(application).ingredienteDao()
        repository = IngredienteRepository(ingredienteDao)
        lista = repository.listado
    }

    fun agregarIngrediente(ingrediente: IngredienteEntity) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.addIngrediente(ingrediente)
        }
    }

    fun actualizarIngrediente(ingrediente: IngredienteEntity) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.updateIngrediente(ingrediente)
        }
    }

    fun eliminarIngrediente(ingrediente: IngredienteEntity) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteIngrediente(ingrediente)
        }
    }

    fun eliminarTodo() {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteAll()
        }
    }
}
=======
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.repository.IngredienteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredienteViewModels(application:
                        Application):AndroidViewModel(application) {
    val lista : LiveData<List<IngredienteEntity>>
    private val repository: IngredienteRepository
    init {
        val ingredienteDao =
            BDPanaderia.getInstance(application).ingredienteDao()
        repository = IngredienteRepository(ingredienteDao)
        lista = repository.listado
    }
    fun agregarIngrediente(ingrediente: IngredienteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addIngrediente(ingrediente)
        }
    }
    fun actualizarIngrediente(ingrediente: IngredienteEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateIngrediente(ingrediente)
        }
    }
    fun eliminarIngrediente(ingrediente: IngredienteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIngrediente(ingrediente)
        }
    }
    fun eliminarTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}
>>>>>>> master
