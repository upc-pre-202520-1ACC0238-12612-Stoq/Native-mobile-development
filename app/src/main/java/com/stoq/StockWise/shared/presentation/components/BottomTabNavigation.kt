package com.stoq.StockWise.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.R
import com.stoq.StockWise.ui.theme.YellowHighlight

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

@Composable
fun MainTabNavigation(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabs: List<TabItem>,
    content: @Composable (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
    ) {
        // Main content area
        Box(
            modifier = Modifier.weight(1f)
        ) {
            content(selectedTabIndex)
        }

        // Bottom navigation
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column {
                // Tab content container
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.White,
                    contentColor = Color(0xFFFF6F00),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            height = 3.dp,
                            color = Color(0xFFFF6F00)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { onTabSelected(index) },
                            text = {
                                Text(
                                    text = tab.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTabIndex == index) Color(0xFFFF6F00) else Color.Gray
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selectedTabIndex == index) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.title,
                                    tint = if (selectedTabIndex == index) Color(0xFFFF6F00) else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            selectedContentColor = Color(0xFFFF6F00),
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabHeader(
    title: String,
    onMenuClick: () -> Unit = {},
    showAddButton: Boolean = false,
    onAddClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(YellowHighlight)
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo and title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_store),
                contentDescription = "Stock Wise Logo",
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Stock Wise",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Add button if needed
            if (showAddButton) {
                IconButton(
                    onClick = onAddClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Menu button
            IconButton(
                onClick = onMenuClick
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

// Tab items for the main navigation
val MainTabs = listOf(
    TabItem(
        title = "Agregar",
        unselectedIcon = Icons.Default.Add,
        selectedIcon = Icons.Default.Add
    ),
    TabItem(
        title = "Inventario",
        unselectedIcon = Icons.Default.Info,
        selectedIcon = Icons.Default.Info
    ),
    TabItem(
        title = "Alertas",
        unselectedIcon = Icons.Default.Notifications,
        selectedIcon = Icons.Default.Notifications
    ),
    TabItem(
        title = "Reportes",
        unselectedIcon = Icons.Default.Info,
        selectedIcon = Icons.Default.Info
    )
)