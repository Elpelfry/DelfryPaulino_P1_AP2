package edu.ucne.delfrypaulino_p1_ap2.presentation.venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.VentaEntity
import edu.ucne.delfrypaulino_p1_ap2.data.repository.VentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val repository: VentaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVentas()
    }

    fun onEvent(event: VentaEvent) {

        when (event) {
            is VentaEvent.onClienteChanged -> onClienteChanged(event.cliente)
            is VentaEvent.onGalonesChanged -> onGalonesChanged(event.galones)
            is VentaEvent.onDescuentoGalonChanged -> onDescuentoGalonChanged(event.descuentoGalon)
            is VentaEvent.onPrecioChanged -> onPrecioChanged(event.precio)
            is VentaEvent.selectVenta -> selectedVenta(event.ventaId)
            is VentaEvent.delete -> deleteVenta(event.ventaId)
            VentaEvent.save -> saveVenta()
            VentaEvent.validation -> uiState.value.validation = validation()
            VentaEvent.newVenta -> newVenta()
        }
    }

    private fun onClienteChanged(cliente: String) {
        val regex = Regex("[a-zA-ZñÑ]{0,50}")
        if (cliente.matches(regex)) {
            _uiState.update {
                it.copy(
                    cliente = cliente,
                    clienteError = ""
                )
            }
        }
    }

    private fun onGalonesChanged(galonesStr: String) {
        val regex = Regex("[0-9]{0,5}\\.?[0-9]{0,2}")
        if (galonesStr.matches(regex)) {
            val galones = galonesStr.toDoubleOrNull() ?: 0.0
            _uiState.update {
                it.copy(
                    galones = galones,
                    galonesError = ""
                )
            }
            calcularTotalDescontado()
            calcularTotal()
        }

    }

    private fun onDescuentoGalonChanged(descuentoGalonStr: String) {
        val regex = Regex("[0-9]{0,5}\\.?[0-9]{0,2}")
        if (descuentoGalonStr.matches(regex)) {
            val descuentoGalon = descuentoGalonStr.toDoubleOrNull() ?: 0.0
            if(descuentoGalon >= 0.0){
                _uiState.update {
                    it.copy(
                        descuentoGalon = descuentoGalon,
                        descuentoGalonError = ""
                    )
                }
                calcularTotalDescontado()
                calcularTotal()
            }
        }
    }

    private fun onPrecioChanged(precioStr: String) {
        val regex = Regex("[0-9]{0,5}\\.?[0-9]{0,2}")
        if (precioStr.matches(regex)) {
            val precio = precioStr.toDoubleOrNull() ?: 0.0
            _uiState.update {
                it.copy(
                    precio = precio,
                    precioError = ""
                )
            }
            calcularTotalDescontado()
            calcularTotal()
        }
    }

    private fun getVentas() {
        viewModelScope.launch {
            repository.getAll().collect { venta ->
                _uiState.update {
                    it.copy(ventas = venta)
                }
            }
        }
    }

    private fun selectedVenta(ventaId: Int) {
        viewModelScope.launch {
            if (ventaId > 0) {
                val venta = repository.find(ventaId)
                if (venta != null) {
                    _uiState.update {
                        it.copy(
                            ventaId = venta.ventaId!!,
                            cliente = venta.cliente,
                            galones = venta.galones,
                            descuentoGalon = venta.descuentoGalon,
                            precio = venta.precio,
                            totalDescontado = venta.totalDescontado,
                            total = venta.total
                        )
                    }
                }
            }
        }
    }

    private fun saveVenta() {
        viewModelScope.launch {
            val venta = uiState.value.toEntity()
            repository.save(venta)
            getVentas()
        }
    }

    private fun newVenta(){
        _uiState.update {
            it.copy(
                cliente = "",
                galones = 0.0,
                descuentoGalon = 0.0,
                precio = 0.0,
                totalDescontado = 0.0,
                total = 0.0,
                ventaId = null,
                clienteError = "",
                galonesError = "",
                descuentoGalonError = "",
                precioError = ""
            )
        }
    }

    private fun deleteVenta(ventaId: Int) {
        viewModelScope.launch {
            val venta = repository.find(ventaId)
            if (venta != null) {
                repository.delete(venta)
                getVentas()
            }
        }
    }

    private fun calcularTotalDescontado() {
        val totalDescontado = (uiState.value.galones ?: 0.0) * (uiState.value.descuentoGalon ?: 0.0)
        _uiState.update {
            it.copy(
                totalDescontado = totalDescontado
            )
        }
    }

    private fun calcularTotal() {
        val total = ((uiState.value.galones ?: 0.0) * (uiState.value.precio ?: 0.0)) - (uiState.value.totalDescontado ?: 0.0)
        _uiState.update {
            it.copy(
                total = total
            )
        }
    }

    private fun validation() : Boolean {
        var validation = true
        val regex = Regex("[a-zA-ZñÑ]{0,50}")
        if (uiState.value.cliente.isBlank() || !uiState.value.cliente.matches(regex)) {
            _uiState.update {
                it.copy(
                    clienteError = "Es requerido y no debe tener caracteres"
                )
            }
            validation = false
        }
        if (uiState.value.galones!! <= 0.0
            || uiState.value.galones == null) {
            _uiState.update {
                it.copy(
                    galonesError = "Debe ser mayor a 0.0"
                )
            }
            validation = false
        }
        if (uiState.value.descuentoGalon!! > uiState.value.precio!!) {
            _uiState.update {
                it.copy(
                    descuentoGalonError = "Descuento debe  menor al precio"
                )
            }
            validation = false
        }
        if (uiState.value.precio!! <= 0.0) {
            _uiState.update {
                it.copy(
                    precioError = "Precio debe ser mayor a 0.0"
                )
            }
            validation = false
        }

        return validation
    }

}

fun UiState.toEntity() = VentaEntity(
    cliente = cliente,
    galones = galones,
    descuentoGalon = descuentoGalon,
    precio = precio,
    totalDescontado = totalDescontado,
    total = total,
    ventaId = ventaId
)