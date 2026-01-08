package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.PrimaryKey

data class CategoriaEmpleado(
    @PrimaryKey val idEmpleadoCategoria: Long = 0,
    val tipo_empleado: String = ""
)
