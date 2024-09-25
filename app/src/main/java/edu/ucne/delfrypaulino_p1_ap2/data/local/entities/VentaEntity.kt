package edu.ucne.delfrypaulino_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentaEntity(
    @PrimaryKey
    val ventaId: Int? = null,
    val cliente: String = "",
    val galones: Double? = null,
    val descuentoGalon: Double? = null,
    val precio: Double? = null,
    val totalDescontado: Double? =null,
    val total: Double? = null
)
