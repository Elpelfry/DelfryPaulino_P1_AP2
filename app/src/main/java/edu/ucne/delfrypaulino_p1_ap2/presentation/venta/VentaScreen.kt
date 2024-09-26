package edu.ucne.delfrypaulino_p1_ap2.presentation.venta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun VentaScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    goList: () -> Unit,
    ventaId: Int
) {
    val uistate by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        if (ventaId > 0 ) {
            viewModel.onEvent(VentaEvent.selectVenta(ventaId))
            println(ventaId)
        }
    }
    VentaScreenBody(
        uistate = uistate,
        onEvent = viewModel::onEvent,
        goList = goList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaScreenBody(
    uistate: UiState,
    onEvent: (VentaEvent) -> Unit,
    goList: () -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Registro Venta",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { goList() }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                item {
                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        label = { Text(text = "Cliente") },
                        value = uistate.cliente,
                        onValueChange = { onEvent(VentaEvent.onClienteChanged(it)) },
                        isError = (uistate.clienteError != ""),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uistate.clienteError != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uistate.clienteError,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Galones") },
                        value = uistate.galones.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(VentaEvent.onGalonesChanged(it))
                        },
                        isError = (uistate.galonesError != ""),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uistate.galonesError != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uistate.galonesError,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Descuento por galón") },
                        value = uistate.descuentoGalon.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(VentaEvent.onDescuentoGalonChanged(it))
                        },
                        isError = (uistate.descuentoGalonError != ""),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uistate.descuentoGalonError != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uistate.descuentoGalonError,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Precio") },
                        value = uistate.precio.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(VentaEvent.onPrecioChanged(it))
                        },
                        isError = (uistate.precioError != ""),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uistate.precioError != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uistate.precioError,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Total Descontado") },
                        value = uistate.totalDescontado.toString().replace("null", "0.0"),
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Total") },
                        value = uistate.total.toString().replace("null", "0.0"),
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = {
                                onEvent(VentaEvent.newVenta)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "New"
                            )
                            Text(text = "Nuevo")
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedButton(
                            onClick = {
                                onEvent(VentaEvent.validation)
                                if (uistate.validation) {
                                    onEvent(VentaEvent.save)
                                    goList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun VentaScreenPreview() {
    VentaScreenBody(
        uistate = UiState(),
        goList = {},
        onEvent = {}
    )
}