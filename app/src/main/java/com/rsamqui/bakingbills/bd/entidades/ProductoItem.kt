package com.rsamqui.bakingbills.bd.entidades

data class ProductoItem (
    val idProducto: Int,
    val nombre: String,
    val descripcion: String,
    val cantidad: Double,
    val precio: Double,
    val peso: Double
        )