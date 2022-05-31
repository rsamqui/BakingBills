package com.rsamqui.bakingbills.api.dataclass

import com.google.gson.annotations.SerializedName

data class Usuarios (
    @SerializedName("idUsuario" ) var idUsuario     : Int?    = null,
    @SerializedName("username"  ) var username       : String? = null,
    @SerializedName("nombres"   ) var nombres      : String? = null,
    @SerializedName("apellidos" ) var apellidos  : String? = null,
    @SerializedName("correo"    ) var correo       : String? = null,
    @SerializedName("pwd"       ) var pwd        : String? = null
)