package com.stoq.StockWise.inventory.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

// Datos de ejemplo para sedes
val sampleBranches = listOf(
    Branch("Sucursal Central", LatLng(-12.0464, -77.0428)),
    Branch("Sucursal Norte", LatLng(-12.0264, -77.0328)),
    Branch("Sucursal Sur", LatLng(-12.0664, -77.0528))
)

data class Branch(val name: String, val location: LatLng)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SedesMapScreen(
    modifier: Modifier = Modifier,
    branches: List<Branch> = sampleBranches,
    onBack: () -> Unit = {}
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(branches.first().location, 12f)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sedes y sucursales") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                branches.forEach { branch ->
                    Marker(
                        state = MarkerState(position = branch.location),
                        title = branch.name
                    )
                }
            }
        }
    }
}