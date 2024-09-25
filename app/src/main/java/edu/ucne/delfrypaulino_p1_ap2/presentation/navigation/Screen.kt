package edu.ucne.delfrypaulino_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object VentaListScreen: Screen()
    @Serializable
    data class VentaScreen(val id: Int): Screen()
}