package com.rsamqui.bakingbills.bd.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rsamqui.bakingbills.bd.dao.BDPanaderia
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.repository.PresupuestoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresupuestoViewModels(
    application: Application): AndroidViewModel(application) {
    val lista : LiveData<List<PresupuestoEntity>>
    private val repository: PresupuestoRepository
    init {
        val presupuestoDao =
            BDPanaderia.getInstance(application).presupuestoDao()
        repository = PresupuestoRepository(presupuestoDao)
        lista = repository.listado
    }
    fun agregarPresupuesto(presupuesto: PresupuestoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPresupuesto(presupuesto)
        }
    }
    fun actualizarPresupuesto(presupuesto: PresupuestoEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updatePresupuesto(presupuesto)
        }
    }
    fun eliminarPresupuesto(presupuesto: PresupuestoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePresupuesto(presupuesto)
        }
    }
    fun eliminarTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}