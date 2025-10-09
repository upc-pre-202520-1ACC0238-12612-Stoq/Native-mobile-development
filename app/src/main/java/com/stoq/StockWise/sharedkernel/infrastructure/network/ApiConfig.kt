package com.stoq.StockWise.sharedkernel.infrastructure.network

/**
 * Configuration for API endpoints used across all bounded contexts.
 * This belongs to Shared Kernel as it's a common concern for all contexts.
 */
object ApiConfig {
    const val BASE_URL = "http://34.39.181.148:8080"
    const val API_VERSION = "api"

    // Token de autenticación temporal
    const val AUTH_TOKEN = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NjA1NzYxODIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6IjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiS2V2aW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbmlzdHJhdG9yIiwicm9sZSI6IkFkbWluaXN0cmF0b3IiLCJpYXQiOjE3NTk5NzEzODIsIm5iZiI6MTc1OTk3MTM4Mn0.MSuOVMRg2Vfdpg-vDHOqKg5SXOpUKVtmruXPk0toMcs"

    // Timeout configurations
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L

    // Endpoints by context
    object IdentityAccess {
        private const val BASE = "$API_VERSION/auth"
        const val LOGIN = "$BASE/login"
        const val REGISTER = "$BASE/register"
        const val REFRESH_TOKEN = "$BASE/refresh"
        const val PROFILE = "$BASE/profile"
    }

    object Inventory {
        private const val BASE = "$API_VERSION"
        const val PRODUCTS = "$BASE/products"
        const val PRODUCT_DETAIL = "$BASE/products/{id}"
        const val INVENTORY = "$API_VERSION/inventory"
        const val INVENTORY_MOVEMENTS = "$API_VERSION/inventory/movements"
    }

    object Sales {
        private const val BASE = "$API_VERSION"
        const val ORDERS = "$BASE/orders"
        const val ORDER_DETAIL = "$BASE/orders/{id}"
        const val CREATE_ORDER = "$BASE/orders"
        const val UPDATE_ORDER_STATUS = "$BASE/orders/{id}/status"
    }
}
