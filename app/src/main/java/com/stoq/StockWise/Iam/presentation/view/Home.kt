package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.ui.theme.YellowHighlight
import com.stoq.StockWise.R
import com.stoq.StockWise.ui.theme.OrangePrimary
import com.stoq.StockWise.ui.theme.RedAccent

data class ProductPreview(val name: String, val date: String, val stock: Int)

@Composable
fun HomeScreen(
    goToLogin: () -> Unit,
    goToProfile: () -> Unit,
    goToProducts: () -> Unit,
    goToCreateInventory: () -> Unit,
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
                modifier = Modifier.size(80.dp)
            )

            Box {
                var showMenu by remember { mutableStateOf(false) }

                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Mi Perfil",
                                    tint = OrangePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Mi Perfil",
                                    color = OrangePrimary
                                )
                            }
                        },
                        onClick = {
                            showMenu = false
                            goToProfile()
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Cerrar Sesión",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Cerrar Sesión",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        onClick = {
                            showMenu = false
                            goToLogin()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tarjetas resumen (2x2) - Todas con íconos
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallStatCard(
                    title = "Total Productos",
                    value = "500",
                    modifier = Modifier.weight(1f),
                    onClick = { /* Navegar a lista de productos */ },
                    iconResId = R.drawable.ic_products
                )
                SmallStatCard(
                    title = "Fecha Proveedor",
                    value = "00/00/00",
                    modifier = Modifier.weight(1f),
                    onClick = { /* Navegar a proveedores */ },
                    iconResId = R.drawable.ic_calendar
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallStatCard(
                    title = "Historial\nMovimientos",
                    value = "",
                    modifier = Modifier.weight(1f),
                    onClick = { /* Navegar a historial */ },
                    iconResId = R.drawable.ic_history
                )
                SmallStatCard(
                    title = "Inventario",
                    value = "",
                    modifier = Modifier.weight(1f),
                    onClick = goToCreateInventory,
                    iconResId = R.drawable.ic_inventory
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

        Spacer(modifier = Modifier.height(12.dp))

        // Botones principales
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { goToProducts() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedAccent)
            ) {
                Text(text = "Agregar Productos")
            }

            Button(
                onClick = { /* navegar a Kits Productos */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
            ) {
                Text(text = "Kits Productos")
            }

            Button(
                onClick = { /* navegar a Devolución Productos */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
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
    }
}

@Composable
private fun SmallStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconResId: Int? = null
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        // Disposición horizontal: ícono a la izquierda, texto a la derecha
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Ícono a la izquierda
            iconResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            // Texto a la derecha
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = value,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick
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