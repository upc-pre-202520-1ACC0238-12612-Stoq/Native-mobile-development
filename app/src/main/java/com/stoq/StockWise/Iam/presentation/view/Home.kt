package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.ui.theme.YellowHighlight
import com.stoq.StockWise.R

data class ProductPreview(val name: String, val date: String, val stock: Int)

@Composable
fun HomeScreen(
    goToLogin: () -> Unit,
    goToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    // Lista inyectada desde el ViewModel con productos próximos a vencer.
    productsNearExpiry: List<ProductPreview> = emptyList(),
    onProductClick: (ProductPreview) -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(YellowHighlight)
            .padding(16.dp)
    ) {
        // Cabecera con logo y menú
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_stockwise),
                contentDescription = "Stock Wise Logo",
                modifier = Modifier.size(56.dp)
            )

            IconButton(onClick = { /* abrir drawer o menú */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tarjetas resumen (2x2)
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallStatCard(title = "Total Productos", value = "500", modifier = Modifier.weight(1f))
                SmallStatCard(title = "Fecha Proveedor", value = "00/00/00", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallStatCard(title = "Historial\nMovimientos", value = "", modifier = Modifier.weight(1f))
                SmallStatCard(title = "Inventario", value = "", modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

        Spacer(modifier = Modifier.height(12.dp))

        // Botones principales
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { /* navegar a Agregar Productos */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Agregar Productos")
            }

            Button(
                onClick = { /* navegar a Kits Productos */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "Kits Productos")
            }

            Button(
                onClick = { /* navegar a Devolución Productos */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "Devolución Productos")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección: productos próximos a vencer (sólo si hay datos)
        if (productsNearExpiry.isNotEmpty()) {
            Text(
                text = "Próximos a vencer",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(productsNearExpiry) { product ->
                    ProductCard(product = product, onClick = { onProductClick(product) })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones inferiores de sesión / perfil
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = goToLogin, modifier = Modifier.weight(1f)) {
                Text("Cerrar Sesión")
            }
            Button(onClick = goToProfile, modifier = Modifier.weight(1f)) {
                Text("Mi Perfil")
            }
        }
    }
}

@Composable
private fun SmallStatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun ProductCard(product: ProductPreview, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = product.date, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "stock")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Stock ${product.stock}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}