package com.rsamqui.bakingbills.API.DataClass

import com.google.gson.annotations.SerializedName

data class Productos (
    @SerializedName("idProducto" ) var idProduct   : Int?    = null,
    @SerializedName("nombre"     ) var nombreP      : String? = null,
    @SerializedName("descripcion") var descripcion : String? = null,
    @SerializedName("cantidad"   ) var cantidad    : Double? = null,
    @SerializedName("precio"     ) var precio      : Double? = null,
    @SerializedName("peso"       ) var peso        : Double? = null
)