package com.rsamqui.bakingbills.bd.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rsamqui.bakingbills.bd.entidades.IngredienteEntity
import com.rsamqui.bakingbills.bd.entidades.PresupuestoEntity
import com.rsamqui.bakingbills.bd.entidades.ProductoEntity
import com.rsamqui.bakingbills.bd.entidades.UsuarioEntity

interface MainDataBaseProvider {
    fun usuarioDao(): UsuarioDao
    fun productoDao(): ProductoDao
    fun ingredienteDao(): IngredienteDao
    fun presupuestoDao(): PresupuestoDao
}

@Database(
<<<<<<< HEAD
    entities = [UsuarioEntity::class, ProductoEntity::class, IngredienteEntity::class], version = 2
=======
    entities = [UsuarioEntity::class, ProductoEntity::class, IngredienteEntity::class, PresupuestoEntity::class], version = 4
>>>>>>> master
)
abstract class BDPanaderia : RoomDatabase(), MainDataBaseProvider {
    abstract override fun usuarioDao(): UsuarioDao
    abstract override fun productoDao(): ProductoDao
    abstract override fun ingredienteDao(): IngredienteDao
    abstract override fun presupuestoDao(): PresupuestoDao


    companion object {
        @Volatile
        private var INSTANCE: BDPanaderia? = null
        fun getInstance(context: Context): BDPanaderia {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BDPanaderia::class.java,
                        "main_bdpanaderia"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}