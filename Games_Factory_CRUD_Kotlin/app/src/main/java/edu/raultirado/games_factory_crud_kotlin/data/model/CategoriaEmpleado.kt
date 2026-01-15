package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

enum class TipoEmpleado { Empleado_Normal, Empleado_Admin }

@Entity(
    tableName = "Categoria_Empleado",
    foreignKeys = [
        ForeignKey(
            entity = Empleado::class,
            parentColumns = ["ID_emp"],
            childColumns = ["ID_emp"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class CategoriaEmpleado(
    @PrimaryKey
    @ColumnInfo(name = "ID_emp")
    val idEmp: String = "",

    @ColumnInfo(name = "tipo_empleado")
    val tipoEmpleado: TipoEmpleado = TipoEmpleado.Empleado_Normal
)