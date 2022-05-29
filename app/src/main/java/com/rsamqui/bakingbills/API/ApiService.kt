package com.rsamqui.bakingbills.API

import com.rsamqui.bakingbills.API.DataClass.Ingredientes
import com.rsamqui.bakingbills.API.DataClass.Productos
import com.rsamqui.bakingbills.API.DataClass.Usuarios
import com.rsamqui.bakingbills.API.DataClass.Presupuesto
import okhttp3.RequestBody
import okhttp3.ResponseBody
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

    @POST("ingrediente/agregar")
    suspend fun addIngrediente(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("producto/agregar")
    suspend fun addProducto(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("usuario/agregar")
    suspend fun addUsuario(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("presupuesto/agregar")
    suspend fun addPresupuesto(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("ingrediente/agregar")
    suspend fun editIngrediente(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("producto/agregar")
    suspend fun editProducto(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("usuario/agregar")
    suspend fun editUsuario(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("presupuesto/agregar")
    suspend fun editPresupuesto(@Body requestBody: RequestBody): Response<ResponseBody>

    @DELETE("ingrediente/eliminar/{idIngrediente}")
    suspend fun delIngrediente(@Path("idIngrediente") idI: Int?): Response<Void>
    @DELETE("producto/eliminar/{idProducto}")
    suspend fun delProducto(@Path("idProducto") idP: Int?): Response<Void>
    @DELETE("usuario/eliminar/{idUsuario}")
    suspend fun delUsuario(@Path("idUsuario") idU: Int?): Response<Void>
    @DELETE("presupuesto/eliminar/{idIngrediente}")
    suspend fun delPresupuesto(@Path("idPresupuesto") idB: Int?): Response<Void>

}