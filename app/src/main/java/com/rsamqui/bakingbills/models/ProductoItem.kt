package com.rsamqui.bakingbills.models

data class ProductoItem (
    val idProducto: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int,
    val peso: Int
        )