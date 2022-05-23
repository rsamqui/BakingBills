package com.rsamqui.bakingbills.API

import com.rsamqui.bakingbills.API.DataClass.Ingredientes
import com.rsamqui.bakingbills.API.DataClass.Productos
import com.rsamqui.bakingbills.API.DataClass.Usuarios
import com.rsamqui.bakingbills.API.DataClass.Presupuesto
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("ingrediente/listar")
    suspend fun getAllIngredientes (): ArrayList<Ingredientes>

    @GET("producto/listar")
    suspend fun getAllProductos (): ArrayList<Productos>

    @GET("usuario/listar")
    suspend fun getAllUsuarios (): ArrayList<Usuarios>

    @GET("presupuesto/listar")
    suspend fun getAllPresupuesto (): ArrayList<Presupuesto>
}