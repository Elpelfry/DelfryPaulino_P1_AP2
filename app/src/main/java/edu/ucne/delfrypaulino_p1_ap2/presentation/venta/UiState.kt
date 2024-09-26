package edu.ucne.delfrypaulino_p1_ap2.presentation.venta

import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.VentaEntity

data class UiState(
    val cliente: String = "",
    val galones: Double? = 0.0,
    val descuentoGalon: Double? = 0.0,
    val precio: Double? = 0.0,
    val totalDescontado: Double? = 0.0,
    val total: Double? = 0.0,
    var validation: Boolean = false,
    val ventaId: Int? = null,
    val ventas: List<VentaEntity> = emptyList(),
    val clienteError: String = "",
    val galonesError: String = "",
    val descuentoGalonError: String = "",
    val precioError: String = "",
    val totalDescontadoError: String = "",
    val totalError: String = "",
)
