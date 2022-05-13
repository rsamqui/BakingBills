package com.rsamqui.bakingbills.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Presupuesto")
data class PresupuestoEntity(
    @PrimaryKey(autoGenerate = true)
    val idPresupuesto: Int = 0,
    @ColumnInfo(name = "ingrediente")
    val ingrediente: String,
    @ColumnInfo(name = "unidades")
    val unidades: Double,
    @ColumnInfo(name = "medida")
    val medida: String,
    @ColumnInfo(name = "precio")
    val precio: Double,
    @ColumnInfo(name = "total")
    val total: Double,
    @ColumnInfo(name = "activo")
    val activo: Boolean
) : Parcelable