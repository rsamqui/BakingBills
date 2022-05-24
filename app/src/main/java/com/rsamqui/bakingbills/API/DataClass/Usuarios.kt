package com.rsamqui.bakingbills.API.DataClass

import com.google.gson.annotations.SerializedName

data class Usuarios (
    @SerializedName("idUsuario" ) var idUser     : Int?    = null,
    @SerializedName("nombres"   ) var names      : String? = null,
    @SerializedName("apellidos" ) var lastnames  : String? = null,
    @SerializedName("username"  ) var user       : String? = null,
    @SerializedName("correo"    ) var mail       : String? = null,
    @SerializedName("pwd"       ) var pwd        : String? = null
)