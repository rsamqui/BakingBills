package com.rsamqui.bakingbills.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="Usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val idUsuario:Int = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "nombres")
    val nombres: String,
    @ColumnInfo(name = "apellidos")
    val apellidos: String,
    @ColumnInfo(name = "correo")
    val correo: String,
    @ColumnInfo(name = "pwd")
    val pwd: String,
    @ColumnInfo(name = "activo")
    val activo: Boolean

): Parcelable