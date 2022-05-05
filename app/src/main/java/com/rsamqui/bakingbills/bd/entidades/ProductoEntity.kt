package com.rsamqui.bakingbills.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="Producto")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val idProducto:Int = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "descripcion")
    val descripcion: String,
    @ColumnInfo(name = "cantidad")
    val cantidad: Int,
    @ColumnInfo(name = "precio")
    val precio: Double,
    @ColumnInfo(name = "peso")
    val peso: Double,
    @ColumnInfo(name = "activo")
    val activo: Boolean

): Parcelable
