package com.rsamqui.bakingbills.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="Ingrediente")
data class IngredienteEntity(
    @PrimaryKey(autoGenerate = true)
    val idIngrediente:Int = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "cantidad")
    val cantidad: Int,
    @ColumnInfo(name = "precio")
    val precio: Double,

): Parcelable