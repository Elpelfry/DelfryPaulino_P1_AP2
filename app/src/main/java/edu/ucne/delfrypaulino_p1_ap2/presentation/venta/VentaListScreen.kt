package edu.ucne.delfrypaulino_p1_ap2.presentation.venta

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.VentaEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun VentaListScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    onEdit: (Int) -> Unit,
    onAdd: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VentaListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onEdit = onEdit,
        onAdd = onAdd
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaListBody(
    uiState: UiState,
    onEvent: (VentaEvent) -> Unit,
    onEdit: (Int) -> Unit,
    onAdd: () -> Unit
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Lista Ventas",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd
            ) {
                Icon(Icons.Filled.Add, "Agregar")
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Cliente", modifier = Modifier.weight(0.10f))
                Text(text = "Galones", modifier = Modifier.weight(0.10f))
                Text(text = "Total", modifier = Modifier.weight(0.10f))
            }
            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.ventas, key = { it.ventaId!! }) { venta ->
                    val coroutineScope = rememberCoroutineScope()
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { state ->
                            if (state == SwipeToDismissBoxValue.EndToStart) {
                                coroutineScope.launch {
                                    delay(0.5.seconds)
                                    onEvent(VentaEvent.delete(venta.ventaId!!))
                                }
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.Settled -> Color.White
                                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                                    SwipeToDismissBoxValue.StartToEnd -> TODO()
                                },
                                label = "Changing color"
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        },
                        modifier = Modifier
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEdit(venta.ventaId!!) }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = venta.cliente, modifier = Modifier.weight(0.12f))
                            Text(text = venta.galones.toString(), modifier = Modifier.weight(0.12f))
                            Text(text = venta.total.toString(), modifier = Modifier.weight(0.12f))

                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VentaListPreview() {
    val list = listOf(
        VentaEntity(
            ventaId = 1,
            cliente = "Delfry Ortega",
            galones = 10.0,
            descuentoGalon = 10.0,
            precio = 50.0,
            totalDescontado = 50.0,
            total = 450.0
        ),
        VentaEntity(
            ventaId = 2,
            cliente = "Delfry Paulino",
            galones = 10.0,
            descuentoGalon = 10.0,
            precio = 50.0,
            totalDescontado = 50.0,
            total = 450.0
        )
    )


    VentaListBody(
        uiState = UiState(ventas = list),
        onEvent = {},
        onAdd = {},
        onEdit = {}
    )
}