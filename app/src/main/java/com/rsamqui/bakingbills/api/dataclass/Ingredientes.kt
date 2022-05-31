package com.rsamqui.bakingbills.api.dataclass

import com.google.gson.annotations.SerializedName

data class Ingredientes (
    @SerializedName("idIngrediente") var idIngredient : Int?    = null,
    @SerializedName("nombre"       ) var nombre       : String? = null,
    @SerializedName("cantidad"     ) var cantidad     : Double? = null,
    @SerializedName("precio"       ) var precio       : Double? = null

)