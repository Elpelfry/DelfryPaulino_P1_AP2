package edu.ucne.delfrypaulino_p1_ap2.presentation.venta

sealed interface VentaEvent {
    data object save : VentaEvent
    data class delete (val ventaId: Int): VentaEvent
    data object validation : VentaEvent
    data object newVenta : VentaEvent
    data class onClienteChanged(val cliente : String) : VentaEvent
    data class onGalonesChanged(val galones : String) : VentaEvent
    data class onDescuentoGalonChanged(val descuentoGalon : String) : VentaEvent
    data class onPrecioChanged(val precio : String) : VentaEvent
    data class selectVenta(val ventaId: Int) : VentaEvent
}